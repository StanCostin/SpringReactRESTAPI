package socialinsider.controller;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;
import socialinsider.model.Brand;
import socialinsider.model.BrandDetails;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
public class APISocialInsiderController {

    @RequestMapping(value={"/getBrands"},method={RequestMethod.GET})
    public List<Brand> getBrands() throws IOException {

        URL url = new URL ("https://app.socialinsider.io/api");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + "API_KEY_TEST");
        con.setDoOutput(true);

        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("jsonrpc","2.0");
        jsonToSend.put("id","0");
        jsonToSend.put("method","socialinsider_api.get_brands");
        JSONObject params = new JSONObject();
        params.put("projectname", "API_test");
        jsonToSend.put("params", params);

        DataOutputStream os = new DataOutputStream(con.getOutputStream());
        os.writeBytes(jsonToSend.toString());
        List<Brand> brands = new ArrayList<Brand>();

        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }
            JSONObject responseJSON = new JSONObject(response.toString());
            JSONArray thisBrand = responseJSON.getJSONArray("result");
            Brand brand;
            BrandDetails brandDetails;

            for(Object obj:thisBrand){

                JSONObject allBrands = (JSONObject) obj;
                brand = new Brand();
                brand.setBrandName(allBrands.get("brandname").toString());
                JSONArray allProfiles = (JSONArray) allBrands.get("profiles");
                brand.setTotalProfiles(allProfiles.length());

                int totalFans = 0;
                int totalEngagements = 0;
                for(int index=0; index<allProfiles.length(); index++) {
                    String profileType = allProfiles.getJSONObject(index).get("profile_type").toString();
                    brandDetails = new BrandDetails();
                    brandDetails.setProfile_type(profileType);
                    String brandId = allProfiles.getJSONObject(index).get("id").toString();
                    brandDetails.setId(brandId);
                    getFollowersAndEngagements(brandDetails);
                    totalEngagements += brandDetails.getEngagements();
                    totalFans += brandDetails.getFans();

                }

                brand.setTotalFans(totalFans);
                brand.setTotalEngagements(totalEngagements);
                brands.add(brand);

            }
        }
        return brands;
    }

    public BrandDetails getFollowersAndEngagements(BrandDetails brandDetails) throws IOException {
        URL url = new URL ("https://app.socialinsider.io/api");
        HttpURLConnection con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
        con.setRequestProperty("Accept", "application/json");
        con.setRequestProperty(HttpHeaders.AUTHORIZATION, "Bearer " + "API_KEY_TEST");
        con.setDoOutput(true);

        JSONObject jsonToSend = new JSONObject();
        jsonToSend.put("id","1");
        jsonToSend.put("method","socialinsider_api.get_profile_data");
        JSONObject params = new JSONObject();
        params.put("id", brandDetails.getId());
        params.put("profile_type", brandDetails.getProfile_type());
        JSONObject date = new JSONObject();
        date.put("start", "1608209422374");
        date.put("end", "1639745412436");
        date.put("timezone", "Europe/London");
        params.put("date", date);
        jsonToSend.put("params", params);

        String dateMilis = "1608209422374";
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date milisToDate = new Date(Long.parseLong(dateMilis));
        String toDate = dateFormat.format(milisToDate);
        DataOutputStream os = new DataOutputStream(con.getOutputStream());
        os.writeBytes(jsonToSend.toString());


        try(BufferedReader br = new BufferedReader(
                new InputStreamReader(con.getInputStream(), "utf-8"))) {
            StringBuilder response = new StringBuilder();
            String responseLine = null;
            while ((responseLine = br.readLine()) != null) {
                response.append(responseLine.trim());
            }

            JSONObject responseJSON = new JSONObject(response.toString());
            JSONObject profile = responseJSON.getJSONObject("resp").getJSONObject(brandDetails.getId()).getJSONObject(toDate);

            brandDetails.setFans(profile.getInt("followers"));
            brandDetails.setEngagements(profile.getInt("engagement"));

        }
        return brandDetails;
    }

}