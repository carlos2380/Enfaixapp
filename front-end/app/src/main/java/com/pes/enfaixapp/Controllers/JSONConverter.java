package com.pes.enfaixapp.Controllers;

import android.text.Html;

import com.pes.enfaixapp.Models.Colla;
import com.pes.enfaixapp.Models.Esdeveniment;
import com.pes.enfaixapp.Models.Noticia;
import com.pes.enfaixapp.Models.RankingItem;
import com.pes.enfaixapp.Models.Usuari;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public static List<String> toCollesUsuari(JSONObject jsonObject) {
        ArrayList<String> collaList = new ArrayList<>();
        try {
            JSONArray jsonColles = jsonObject.getJSONArray("belongs");
            for (int i = 0; i < jsonColles.length(); ++i) {
                JSONObject jsonColla = jsonColles.getJSONObject(i);
                collaList.add(String.valueOf(jsonColla.getJSONObject("name")));
                //collaList.add(jsonColla.getString("name"));

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        /*collaList.add("Castellers de Vilafranca");
        collaList.add("Arreplegats de la Zona Universitària");*/
        return collaList;
    }

    public static List<Noticia> toNoticies(JSONObject jsonObject) throws Exception {
        List<Noticia> noticias = new ArrayList<Noticia>();
        if(jsonObject != null) {
            try {
                Iterator iter = jsonObject.keys();
                boolean continuar = true;
                while (iter.hasNext() && continuar) {

                    String key = (String) iter.next();
                    if (key != "connection") {
                        JSONObject objNoticia = jsonObject.getJSONObject(key);
                        Noticia noticia = new Noticia();
                        noticia.setTitulo(Html.fromHtml(objNoticia.getString("title")).toString());
                        noticia.setUrl(Html.fromHtml(objNoticia.getString("link")).toString());
                        noticia.setDescription(Html.fromHtml(objNoticia.getString("description")).toString());
                        noticia.setDate(DateUtils.parseStringCompleteToStringSimple(Html.fromHtml(objNoticia.getString("date")).toString()));
                        noticias.add(noticia);
                    } else {
                        continuar = false;
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                throw new Exception("Internal Error: Converter Json to News");
            }
        }
        return noticias;
    }

    public static List<Esdeveniment> toEsdeveniments(JSONObject jsonObject) throws Exception {
        List<Esdeveniment> esdeveniments = new ArrayList<Esdeveniment>();
        if(jsonObject != null) {
            try {
                JSONArray jsonEsdevs = jsonObject.getJSONArray("array");
                for (int i = 0; i < jsonEsdevs.length(); ++i) {
                    JSONObject jsonEsdev = jsonEsdevs.getJSONObject(i);
                    Esdeveniment esdeveniment = new Esdeveniment();
                    esdeveniment.setDireccio(jsonEsdev.getString("address"));
                    esdeveniment.setColla(jsonEsdev.getInt("colla_id"));
                    esdeveniment.setDate(DateUtils.parseStringCompleteToStringSimple(jsonEsdev.getString("date")));
                    esdeveniment.setDescripcio(jsonEsdev.getString("description"));
                    esdeveniment.setId(jsonEsdev.getInt("id"));
                    esdeveniment.setFoto(jsonEsdev.getString("img"));
                    esdeveniment.setUsuari(jsonEsdev.getInt("user_id"));
                    esdeveniment.setTitol(jsonEsdev.getString("title"));
                    esdeveniments.add(esdeveniment);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                throw new Exception("Internal Error: Converter Json to Esdeveniment");
            }
        }
        return esdeveniments;
    }

    public static Colla toColla(JSONObject output) throws JSONException {
        Colla colla = new Colla();
        JSONObject jsonColla = output;

        colla.setId(jsonColla.getInt("id"));
        colla.setName(Html.fromHtml(jsonColla.getString("name")).toString());
        colla.setColor(jsonColla.getString("color"));
        colla.setImagen(jsonColla.getString("img"));
        colla.setDireccio(Html.fromHtml(jsonColla.getString("address")).toString());
        colla.setDescripcio(Html.fromHtml(jsonColla.getString("description")).toString());
        colla.setEmail(jsonColla.getString("email"));
        colla.setTelefono(jsonColla.getString("phoneNumber"));
        colla.setWeb(jsonColla.getString("web"));
        if (jsonColla.getInt("uni") == 1) {
            colla.setUniversitaria(true);
        } else {
            colla.setUniversitaria(false);
        }
        return colla;
    }

    public static List<Colla> toCollaList(JSONObject output) {
        ArrayList<Colla> collaList = new ArrayList<Colla>();
        try {
            JSONArray jsonColles = output.getJSONArray("array");
            for (int i = 0; i < jsonColles.length(); ++i) {
                JSONObject jsonColla = jsonColles.getJSONObject(i);
                Colla colla = new Colla();
                colla.setId(jsonColla.getInt("id"));
                colla.setName(Html.fromHtml(jsonColla.getString("name")).toString());
                colla.setColor(jsonColla.getString("color"));
                colla.setImagen(jsonColla.getString("img"));
                colla.setDireccio(Html.fromHtml(jsonColla.getString("address")).toString());
                colla.setDescripcio(Html.fromHtml(jsonColla.getString("description")).toString());
                colla.setEmail(jsonColla.getString("email"));
                colla.setTelefono(jsonColla.getString("phoneNumber"));
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

    public static List<RankingItem> toRankingList(JSONObject output) {
        ArrayList<RankingItem> rankingList = new ArrayList<RankingItem>();
        try {
            JSONArray jsonRanking = output.getJSONArray("array");
            for (int i = 0; i < jsonRanking.length(); ++i) {
                JSONObject jsonInputRanking = jsonRanking.getJSONObject(i);
                RankingItem input = new RankingItem();
                input.setName(jsonInputRanking.getString("name"));
                input.setPunctuation(jsonInputRanking.getInt("punctuation"));
                input.setPos(jsonInputRanking.getInt("pos"));
                rankingList.add(input);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return rankingList;
    }

}
