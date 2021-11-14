package com.aldlflcl.location.domain.location;

import com.aldlflcl.location.domain.picture.Picture;
import com.aldlflcl.location.domain.review.Review;
import com.aldlflcl.location.domain.review.ReviewForm;
import com.aldlflcl.location.domain.user.User;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Location {

    private int locationId;

    private List<Picture> pictures;

    private List<ReviewForm> reviews;

    private String title;

    private String content;

    private Float addrX;

    private Float addrY;

    private User user;

    public Location() {
    }

    public Location(LocationForm form) {
        this.title = form.getTitle();
        this.content = form.getContent();
        this.addrX = form.getX();
        this.addrY = form.getY();
    }

    /*public static Float[] findGeoPoint(String location) {
        final String API_ADDRESS = "https://dapi.kakao.com/v2/local/search/keyword.json?query=";
        final String API_KEY = "KakaoAK 4ff5d1f33d0fa7505bd3713ec911e213";
        URL obj;
        try {
            String address = URLEncoder.encode(location, "UTF-8");
            obj = new URL(API_ADDRESS + address);

            HttpURLConnection con = (HttpURLConnection) obj.openConnection();

            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", API_KEY);
            con.setRequestProperty("content-type", "application/json");
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setDefaultUseCaches(false);

            Charset charset = Charset.forName("UTF-8");

            BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream(), charset));

            String inputLine;

            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }

            System.out.println(response);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }*/
}
