select * from tbl_usuarios;

update tbl_usuarios set habilitado = true where usuario_id = 1;

select * from tbl_roles;

select * from tbl_usuario_roles;

select u.email, r.nombre from tbl_usuarios u inner join tbl_usuario_roles ur on (u.usuario_id = ur.usuario_id) inner join tbl_roles r on (r.rol_id = ur.rol_id)
where email = 'alexisgutierrezf.1997@gmail.com';

select u.email, r.nombre from tbl_usuarios u inner join tbl_usuario_roles ur on(u.usuario_id=ur.usuario_id) inner join tbl_roles r on(ur.rol_id=r.rol_id) where u.email='alexisgutierrezf.1997@gmail.com';

insert into tbl_usuario_roles (usuario_id, rol_id) values(1,2);

select email, password, '1' as enabled from tbl_usuarios where email= 'alexisgutierrezf.1997@gmail.com' and habilitado = 'true';

