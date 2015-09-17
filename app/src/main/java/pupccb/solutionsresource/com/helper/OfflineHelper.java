package pupccb.solutionsresource.com.helper;

import android.content.Context;
import android.os.AsyncTask;

import pupccb.solutionsresource.com.helper.communicator.OfflineCommunicator;

/**
 * Created by User on 8/17/2015.
 */
public class OfflineHelper extends BaseHelper implements OfflineCommunicator {

    private Controller controller;

    protected class processTicket extends AsyncTask<String, Void,String>{
        private Context context;
        private String id;
        private Controller.MethodTypes methodTypes;

        public processTicket(Context context, String id, Controller.MethodTypes methodTypes){
            this.context = context;
            this.id = id;
            this.methodTypes = methodTypes;

        }

        @Override
        protected void onPreExecute() {
            openDatabase(context);
        }

        private long result = 0;

        @Override
        protected String doInBackground(String... params) {
            switch (methodTypes){

            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            closeDatabase();
            switch (methodTypes){

            }
        }
    }

}
