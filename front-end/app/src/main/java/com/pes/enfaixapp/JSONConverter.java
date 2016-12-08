package com.pes.enfaixapp;

import android.graphics.Color;
import android.text.Html;

import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.Models.Noticia;
import com.pes.enfaixapp.Models.Usuari;

import org.json.JSONArray;
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
            // TODO: Falta parsejar les colles
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
            while (iter.hasNext() && continuar) {

                String key = (String) iter.next();
                if (jsonObject.getString(key).toString() != "connection") {
                    JSONObject objNoticia = jsonObject.getJSONObject(key);
                    Noticia noticia = new Noticia();
                    noticia.setTitulo(Html.fromHtml(objNoticia.getString("title")).toString());
                    noticia.setUrl(Html.fromHtml(objNoticia.getString("link")).toString());
                    noticia.setDescription(Html.fromHtml(objNoticia.getString("description")).toString());

                    //DateFormat formatter = new SimpleDateFormat("E, dd MMM yyyy HH:mm:ss ZZZZ");
                    //String data = String.valueOf(Html.fromHtml(URLDecoder.decode(objNoticia.getString("date"), "UTF-8")));
                    //Date date = formatter.parse(data);
                    //noticia.setDate(date);
                    noticias.add(noticia);
                } else {
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

    public static List<Colla> toCollaList(JSONObject output) {
        List<Colla> collaList = new ArrayList<>();
        try {
            JSONArray jsonColles = output.getJSONArray("array");
            for (int i = 0; i < jsonColles.length(); ++i) {
                JSONObject jsonColla = jsonColles.getJSONObject(i);
                Colla colla = new Colla();
                colla.setId(jsonColla.getInt("id"));
                colla.setName(jsonColla.getString("name"));
                if (jsonColla.getInt("uni") == 1) {
                    colla.setUniversitaria(true);
                } else {
                    colla.setUniversitaria(false);
                }
                collaList.add(colla);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return collaList;
    }

}
