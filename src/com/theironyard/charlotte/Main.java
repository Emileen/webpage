package com.theironyard.charlotte;

import spark.ModelAndView;
import spark.Session;
import spark.Spark;
import spark.template.mustache.MustacheTemplateEngine;

import java.util.HashMap;

public class Main {
    static User user;
    static HashMap m = new HashMap();

    public static void main(String[] args) {
        HashMap<String, User> users = new HashMap<>();

        Spark.init();

        Spark.get("/", ((request, response) -> {
                    Session session = request.session();
                    String userName = session.attribute("userName");
                    User user = users.get(userName);
                    if (user == null) {
                        return new ModelAndView(m, "Login.html");
                    } else {
                        m.put("name", user.name);
                        return new ModelAndView(m, "Table.html");

                        //return new ModelAndView(m,"Home.html");
                    }

                }),
                new MustacheTemplateEngine()
        );


        Spark.get("/aboutme", ((request, response) -> {
                    return new ModelAndView(m, "aboutme.html");

                }),
                new MustacheTemplateEngine());


        Spark.get("/funfacts", ((request, response) -> {

            return new ModelAndView(m, "FunFacts.html");

        }), new MustacheTemplateEngine());

        Spark.get("/newadventure", ((request, response) -> {

            return new ModelAndView(m, "NewAdventure.html");

        }),
                new MustacheTemplateEngine());

        Spark.get("/", ((request, response) -> {

            return new ModelAndView(m, "");

        }));

        Spark.post("/login", ((request, response) -> {
                    String name = request.queryParams("loginName");
                    if (user == null) {
                        user = new User(name);
                        users.put(name, user);
                    }
                    Session session = request.session();
                    session.attribute("userName", name);
                    response.redirect("/");
                    return "";

                })
        );


        Spark.post("/logout", ((request, response) -> {
            Session session = request.session();
            session.invalidate();
            response.redirect("/");
            return "";
        }));

    }
}
