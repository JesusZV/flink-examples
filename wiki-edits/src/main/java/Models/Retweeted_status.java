package Models;

import java.util.ArrayList;

public class Retweeted_status {
    private String created_at;
    private float id;
    private String id_str;
    private String text;
    ArrayList< Object > display_text_range = new ArrayList< Object >();
    private String source;
    private boolean truncated;
    private float in_reply_to_status_id;
    private String in_reply_to_status_id_str;
    private float in_reply_to_user_id;
    private String in_reply_to_user_id_str;
    private String in_reply_to_screen_name;
    User UserObject;
    private String geo = null;
    private String coordinates = null;
    private String place = null;
    private String contributors = null;
    private boolean is_quote_status;
    private float quote_count;
    private float reply_count;
    private float retweet_count;
    private float favorite_count;
    Entities EntitiesObject;
    private boolean favorited;
    private boolean retweeted;
    private String filter_level;
    private String lang;


    // Getter Methods

    public String getCreated_at() {
        return created_at;
    }

    public float getId() {
        return id;
    }

    public String getId_str() {
        return id_str;
    }

    public String getText() {
        return text;
    }

    public String getSource() {
        return source;
    }

    public boolean getTruncated() {
        return truncated;
    }

    public float getIn_reply_to_status_id() {
        return in_reply_to_status_id;
    }

    public String getIn_reply_to_status_id_str() {
        return in_reply_to_status_id_str;
    }

    public float getIn_reply_to_user_id() {
        return in_reply_to_user_id;
    }

    public String getIn_reply_to_user_id_str() {
        return in_reply_to_user_id_str;
    }

    public String getIn_reply_to_screen_name() {
        return in_reply_to_screen_name;
    }

    public User getUser() {
        return UserObject;
    }

    public String getGeo() {
        return geo;
    }

    public String getCoordinates() {
        return coordinates;
    }

    public String getPlace() {
        return place;
    }

    public String getContributors() {
        return contributors;
    }

    public boolean getIs_quote_status() {
        return is_quote_status;
    }

    public float getQuote_count() {
        return quote_count;
    }

    public float getReply_count() {
        return reply_count;
    }

    public float getRetweet_count() {
        return retweet_count;
    }

    public float getFavorite_count() {
        return favorite_count;
    }

    public Entities getEntities() {
        return EntitiesObject;
    }

    public boolean getFavorited() {
        return favorited;
    }

    public boolean getRetweeted() {
        return retweeted;
    }

    public String getFilter_level() {
        return filter_level;
    }

    public String getLang() {
        return lang;
    }

    // Setter Methods

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public void setId(float id) {
        this.id = id;
    }

    public void setId_str(String id_str) {
        this.id_str = id_str;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setTruncated(boolean truncated) {
        this.truncated = truncated;
    }

    public void setIn_reply_to_status_id(float in_reply_to_status_id) {
        this.in_reply_to_status_id = in_reply_to_status_id;
    }

    public void setIn_reply_to_status_id_str(String in_reply_to_status_id_str) {
        this.in_reply_to_status_id_str = in_reply_to_status_id_str;
    }

    public void setIn_reply_to_user_id(float in_reply_to_user_id) {
        this.in_reply_to_user_id = in_reply_to_user_id;
    }

    public void setIn_reply_to_user_id_str(String in_reply_to_user_id_str) {
        this.in_reply_to_user_id_str = in_reply_to_user_id_str;
    }

    public void setIn_reply_to_screen_name(String in_reply_to_screen_name) {
        this.in_reply_to_screen_name = in_reply_to_screen_name;
    }

    public void setUser(User userObject) {
        this.UserObject = userObject;
    }

    public void setGeo(String geo) {
        this.geo = geo;
    }

    public void setCoordinates(String coordinates) {
        this.coordinates = coordinates;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public void setContributors(String contributors) {
        this.contributors = contributors;
    }

    public void setIs_quote_status(boolean is_quote_status) {
        this.is_quote_status = is_quote_status;
    }

    public void setQuote_count(float quote_count) {
        this.quote_count = quote_count;
    }

    public void setReply_count(float reply_count) {
        this.reply_count = reply_count;
    }

    public void setRetweet_count(float retweet_count) {
        this.retweet_count = retweet_count;
    }

    public void setFavorite_count(float favorite_count) {
        this.favorite_count = favorite_count;
    }

    public void setEntities(Entities entitiesObject) {
        this.EntitiesObject = entitiesObject;
    }

    public void setFavorited(boolean favorited) {
        this.favorited = favorited;
    }

    public void setRetweeted(boolean retweeted) {
        this.retweeted = retweeted;
    }

    public void setFilter_level(String filter_level) {
        this.filter_level = filter_level;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }
}
