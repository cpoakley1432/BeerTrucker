package com.company;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
        ArrayList<Beer> beers = new ArrayList();
        Spark.get(
                "/",
                ((request, response) -> {
                    Session session = request.session();
                    String username = session.attribute("username");
                    if (username ==null){
                        return new ModelAndView(new HashMap(), "not-logged-in.html");
                    }
                    HashMap m = new HashMap();
                    m.put("username", username );
                    return new ModelAndView(m, "logged-in.html");
                }),
                new MustacheTemplateEngine()
        );
        Spark.post(
                "/login",
                ((request, response) -> {
                    String userName = request.queryParams("username");
                    Session session = request.session();
                    session.attribute("username",userName);
                    response.redirect("/");
                    return "";
                })
        );
        Spark.post(
                "/create-beer",
                ((request, response) ->{
                    Beer beer = new Beer();
                    beer.id = beers.size() + 1;
                    beer.name = request.queryParams("beername");
                    beer.type = request.queryParams("beertype");
                    beers.add(beer);
                    response.redirect("/");
                    return "";
                })
        );

    }
}
