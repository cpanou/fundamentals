package com.company.garage.application;

import com.company.garage.controllers.CarsResource;
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Logger;


@ApplicationPath("/garage")
public class MyApplication extends Application {
    private static final Logger LOGGER = Logger.getLogger(MyApplication.class.getName());

    @Override
    public Set<Class<?>> getClasses() {
        HashSet set = new HashSet<Class<?>>();
        set.add(JacksonJsonProvider.class);
        set.add(CarsResource.class);
        return set;
    }

}
