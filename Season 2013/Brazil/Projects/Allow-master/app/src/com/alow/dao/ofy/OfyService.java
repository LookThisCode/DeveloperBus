package com.alow.dao.ofy;

import com.alow.model.Alow;
import com.alow.model.Grupo;
import com.alow.model.Pessoa;
import com.alow.model.Photo;
import com.alow.model.Theme;
import com.alow.model.Vote;
import com.alow.model.plus.DirectedUserToUserEdge;
import com.alow.model.plus.User;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyFactory;
import com.googlecode.objectify.ObjectifyService;

public class OfyService {
    static {
        factory().register(Pessoa.class);
        factory().register(Grupo.class);
        factory().register(Alow.class);
        
        factory().register(DirectedUserToUserEdge.class);
        factory().register(Photo.class);
        factory().register(Theme.class);
        factory().register(User.class);
        factory().register(Vote.class);

    }

    public static Objectify ofy() {
        return ObjectifyService.ofy();
    }

    public static ObjectifyFactory factory() {
        return ObjectifyService.factory();
    }
}