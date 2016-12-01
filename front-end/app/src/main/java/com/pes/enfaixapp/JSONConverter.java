package com.pes.enfaixapp;

import android.text.Html;

import com.pes.enfaixapp.Models.Noticia;
import com.pes.enfaixapp.Models.Usuari;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

    public static List<Noticia> toNoticies(JSONObject jsonObject) throws UnsupportedEncodingException {
        List<Noticia> noticias = new ArrayList<Noticia>();
        try {
            Iterator iter = jsonObject.keys();
            boolean continuar = true;
            while(iter.hasNext() && continuar){

                String key = (String)iter.next();
                if(jsonObject.getString(key).toString() != "connection") {
                    JSONObject objNoticia = jsonObject.getJSONObject(key);
                    Noticia noticia= new Noticia();
                    noticia.setTitulo(URLDecoder.decode(objNoticia.getString("title"), "UTF-8"));
                    noticia.setUrl(URLDecoder.decode(objNoticia.getString("link"), "UTF-8"));

                    noticia.setDescription(String.valueOf(Html.fromHtml(URLDecoder.decode(objNoticia.getString("description"), "UTF-8"))));

                    //DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss ZZZZ");
                    //String data = String.valueOf(Html.fromHtml(URLDecoder.decode(objNoticia.getString("date"), "UTF-8")));
                    //Date date = formatter.parse(data);
                    //noticia.setDate(date);
                    noticias.add(noticia);
                }else {
                    continuar = false;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } /*catch (ParseException e) {
            e.printStackTrace();
        }*/
        return noticias;
    }
}
