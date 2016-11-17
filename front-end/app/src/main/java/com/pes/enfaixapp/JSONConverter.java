package com.pes.enfaixapp;

import com.pes.enfaixapp.Models.Noticia;
import com.pes.enfaixapp.Models.Usuari;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by eduard on 17/11/16.
 */
public class JSONConverter {
    public static Usuari toUser(JSONObject jsonObject) {
        Usuari usuari = new Usuari();
        try {
            usuari.setId(jsonObject.getInt("id"));
            usuari.setNom(jsonObject.getString("name"));
            usuari.setCognoms(jsonObject.getString("surname"));
            usuari.setCorreu(jsonObject.getString("email"));
            // TODO: Falta parsejar les colles i el session-token
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return usuari;
    }

    public static List<Noticia> toNoticies(JSONObject jsonObject)  {
        List<Noticia> noticias = new ArrayList<Noticia>();
        try {
            Iterator iter = jsonObject.keys();
            boolean continuar = true;
            while(iter.hasNext() && continuar){

                String key = (String)iter.next();
                if(jsonObject.getString(key).toString() != "connection") {
                    JSONObject objNoticia = jsonObject.getJSONObject(key);
                    Noticia noticia= new Noticia();
                    noticia.setTitulo(objNoticia.getString("title"));
                    noticia.setUrl(objNoticia.getString("link"));
                    //noticia.setDate(Date.valueOf(objNoticia.getString("date")));
                    noticia.setDescription(objNoticia.getString("description"));
                    noticias.add(noticia);
                }else {
                    continuar = false;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return noticias;
    }
}
