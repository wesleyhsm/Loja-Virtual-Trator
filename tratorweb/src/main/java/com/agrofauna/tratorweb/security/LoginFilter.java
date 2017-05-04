package com.agrofauna.tratorweb.security;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author wesley
 */
@WebFilter(urlPatterns = {"/ajuda/*", 
						  "/carrinhoDeCompras/*", 
						  "/compraGanhe/*", 
						  "/contato/*", 
						  "/elements/*", 
						  "/encomenda/*",
						  "/finalizacaoDeCompras/*",
						  "/meuCadastro/*",
						  "/minhasCompras/*",
						  "/promocao/*", 
						  "/produto/*",
						  "/troca/*"})

public class LoginFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        //System.out.println("entro no filter");

        HttpServletRequest req = (HttpServletRequest) request;
        HttpSession session = req.getSession();
        if (session.getAttribute("login") != null || req.getRequestURI().endsWith("index.xhtml")) {
            //System.out.println("Com login");
            chain.doFilter(request, response);
        } else {
            //System.out.println("Sem login");
            HttpServletResponse res = (HttpServletResponse) response;
            res.sendRedirect("/tratorweb/index.xhtml");
        }

        //System.out.println("saiu no filter");
    }

    @Override
    public void destroy() {
    }

}