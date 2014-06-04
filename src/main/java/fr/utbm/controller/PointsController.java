package fr.utbm.controller;

import com.google.gson.JsonObject;
import fr.utbm.model.AccessPoint;
import fr.utbm.service.AccessPointService;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import javax.annotation.Resource;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class PointsController {

    @Resource
    AccessPointService apService;

    @RequestMapping(value = "/points/add", method = RequestMethod.GET)
    public @ResponseBody
    String addPoint(@RequestParam Double x, @RequestParam Double y, @RequestParam Integer mapId) {
        JsonObject json = new JsonObject();
        InputStream is = null;

        for (AccessPoint ap : apService.getAllAccessPoints()) {
            try {
                URL url = new URL("http://localhost:8080/getRssi?mac="+ap.getMacAddr());
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000 /* milliseconds */);
                urlConnection.setConnectTimeout(15000 /* milliseconds */);
                urlConnection.setRequestMethod("GET");
                urlConnection.setDoInput(true);

                urlConnection.connect();
                is = urlConnection.getInputStream();
                
	        String contentAsString = readIt(is, is.available());
	        return contentAsString;
            } catch (IOException e) {
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        
                    }
                }
            }
        }

        //json.addProperty("success", Boolean.FALSE);
        json.addProperty("success", Boolean.TRUE);

        return json.toString();
    }

    	// Reads an InputStream and converts it to a String.
	public static String readIt(InputStream stream, int len) throws IOException, UnsupportedEncodingException {
	    Reader reader = null;
	    reader = new InputStreamReader(stream, "UTF-8");        
	    char[] buffer = new char[len];
	    reader.read(buffer);
	    return new String(buffer);
	}
}
