package ffab0.pocketprogramming.models;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;

import org.json.JSONArray;


import ffab0.pocketprogramming.environments.EnvironmentConstants;
import ffab0.pocketprogramming.fragments.ReviewFragment;
import ffab0.pocketprogramming.viewModel.ReviewViewModel;

public class ReviewRepository {

    public void fetchAll(final ReviewFragment reviewFragment, final LayoutInflater inflater, final View fragmentRootView) {
        RequestQueue mQueue = VolleySingleton.getInstance(Common.getContext()).getRequestQueue();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET,
                EnvironmentConstants.HOST_URL + "?uuid=" + UserId.id() + "&lang=" + Common.getLanguage(),
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        ReviewViewModel.list(reviewFragment, inflater, fragmentRootView, response);
                    }
                },

                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("Review.fetchAll", error.toString());
                    }
                }
        );
        request.setRetryPolicy(new DefaultRetryPolicy(12000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

        mQueue.add(request);
    }
}
