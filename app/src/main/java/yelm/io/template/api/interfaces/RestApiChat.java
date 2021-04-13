package yelm.io.template.api.interfaces;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import yelm.io.template.chat.model.ChatMessage;

public interface RestApiChat {

    String URL_API_MAIN = "https://chat.yelm.io/api/message/";

    @GET("all?")
    Call<ArrayList<ChatMessage>> getChatHistory(
            @Query("platform") String Platform,
            @Query("room_id") String RoomID
    );
}