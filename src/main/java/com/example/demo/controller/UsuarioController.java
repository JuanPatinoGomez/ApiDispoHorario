package com.example.demo.controller;

import com.example.demo.entity.Usuario;
import com.example.demo.entity.dto.UsuarioDTO;
import com.example.demo.service.IUsuarioService;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@CrossOrigin(origins = { "http://localhost:4200"})
@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private IUsuarioService usuarioService;

    @GetMapping()
    public ResponseEntity<List<Usuario>> findAll(){

        List<Usuario> usuarios = usuarioService.findAll();

        if(usuarios.size() > 0) {
            return new ResponseEntity<>(usuarios, HttpStatus.OK);
        }else {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

    }

    @PostMapping("/validacion")
    public ResponseEntity<Map<String, Object>> validarUsuario(@Valid @RequestBody UsuarioDTO usuarioDTO){
        //Se construye el usuario
        Usuario usuario = conversionDtoEntidad(usuarioDTO);
        Map<String, Object> response = new HashMap<>();
        Usuario usuarioFromDb = usuarioService.findByUsuario(usuario.getUsuario());
        if(usuarioFromDb == null){
            response.put("validacion", Boolean.FALSE);
            response.put("mensaje", "Credenciales incorrectas");
        }else{
            byte[] contrasenaLogin = usuario.getContrasena();
            String contrasenaLoginString = new String(contrasenaLogin);
            String contrasenaDB = new String(Base64.decodeBase64(usuarioFromDb.getContrasena()));

            if(usuarioFromDb.getHabilitado().equals(Boolean.TRUE)){
                if(contrasenaDB.equals(contrasenaLoginString)){
                    response.put("validacion", Boolean.TRUE);
                    response.put("mensaje", "Credenciales correctas");
                }else{
                    response.put("validacion", Boolean.FALSE);
                    response.put("mensaje", "Credenciales incorrectas");
                }

            }else{
                response.put("validacion", Boolean.FALSE);
                response.put("mensaje", "El usuario no se encuentra habilitado");
            }
        }
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity<Map<String, Object>> insert(@Valid @RequestBody UsuarioDTO usuarioDTO, BindingResult results){
        //Se construye el usuario
        Usuario usuario = conversionDtoEntidad(usuarioDTO);

        Map<String, Object> response = new HashMap<>();

        //Verificamos si hay errores, relacionados a la validacion
        if(results.hasErrors()) {

            List<String> errors = results.getAllErrors().stream()
                    .map(error -> error.getDefaultMessage())
                    .collect(Collectors.toList());

            response.put("errors", errors);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);

        }

        //En caso se que no hayan errores se procede a realizar el insert en la base de datos
        try {
            if(usuario.getId() == null) { // Cuando el usuario es nuevo
                usuario.setHabilitado(Boolean.TRUE);
                usuario.setContrasena(Base64.encodeBase64(usuario.getContrasena()));
            }
            Usuario usuarioFromDb = usuarioService.save(usuario);

            response.put("usuario", usuarioFromDb);
            response.put("mensaje", "Usuario creado de manera exitosas");

            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e) {

            response.put("Mensaje", "El usuario NO ha sido creado de manera exitosa:" + e.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    public Usuario conversionDtoEntidad(UsuarioDTO usuarioDTO){
        Usuario usuario = new Usuario();
        usuario.setId(usuarioDTO.getId() != null ? usuarioDTO.getId() : null);
        usuario.setUsuario(usuarioDTO.getUsuario());
        usuario.setContrasena(usuarioDTO.getContrasena().getBytes());
        usuario.setHabilitado(usuario.getHabilitado() != null ? usuarioDTO.getHabilitado() : null);
        return usuario;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Map<String, Object>> delete(@PathVariable(name = "id")long id){

        Map<String, Object> response = new HashMap<>();

        try {

            //Se consulta en la base de datos si existe el usuario
            Usuario usuario = usuarioService.findById(id);

            //Si no existe se envia el estado not found
            if(usuario == null) {
                response.put("mensaje", "Usuario con el id " + id + " no se encuentra en la base de datos");
                return new ResponseEntity<Map<String,Object>>(HttpStatus.NOT_FOUND);
            }

            //Si existe se procede a eliminar el usuario
            usuarioService.delete(id);

            response.put("mensaje", "Usuario eliminada de manera exitosas");
            return new ResponseEntity<>(response, HttpStatus.OK);

        } catch (DataAccessException e) {
            response.put("Mensaje", "Usuario NO ha sido eliminada de manera exitosa:" + e.getMostSpecificCause());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @GetMapping("/{id}/cambioEstado")
    public ResponseEntity<Usuario> cambioEstado(@PathVariable(name = "id")long id){
        Usuario usuario = usuarioService.findById(id);
        usuario.setHabilitado(!usuario.getHabilitado());
        return new ResponseEntity<>(usuarioService.save(usuario), HttpStatus.OK);
    }
}
