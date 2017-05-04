package com.agrofauna.tratorweb.security;

public class AppUserDetailsService  {
/*
	@Override
	public UserDetails loadUserByUsername(String nmLogin) throws UsernameNotFoundException {
		PessoaRepository pessoaRepository = CDIServiceLocator.getBean(PessoaRepository.class);
		Login login = pessoaRepository.loginCliente(nmLogin);
		
		UsuarioSistema user = null;
		
		if (login != null) {
			user = new UsuarioSistema(login, getGrupos(login));
		}
		
		return user;
	}

	private Collection<? extends GrantedAuthority> getGrupos(Login login) {
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		
		authorities.add(new SimpleGrantedAuthority("CLIENTE"));
		//for (GrupoAcesso grupo : login.getListGrupoAcesso()) {
		//	authorities.add(new SimpleGrantedAuthority(grupo.getNome().toUpperCase()));
		//}
		
		return authorities;
	}*/

}