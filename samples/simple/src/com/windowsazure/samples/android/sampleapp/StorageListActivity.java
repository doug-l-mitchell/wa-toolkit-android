package com.windowsazure.samples.android.sampleapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.windowsazure.samples.android.sampleapp.R;
import com.windowsazure.samples.android.storageclient.CloudBlob;
import com.windowsazure.samples.android.storageclient.CloudBlobContainer;
import com.windowsazure.samples.android.storageclient.CloudQueue;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class StorageListActivity extends SecuredActivity implements OnItemClickListener {
	
	static final String TYPE_NAMESPACE = "com.windowsazure.samples.android.sampleapp.storage_list.type";
	static final String TITLE_NAMESPACE = "com.windowsazure.samples.android.sampleapp.storage_list.title";
	
	static final int LIST_TYPE_TABLE = 1;
	static final int LIST_TYPE_CONTAINER = 2;
	static final int LIST_TYPE_BLOB = 3;
	static final int LIST_TYPE_QUEUE = 4;
	
	List<String> items;
	int listType = 0;
	
    @Override
    public void onCreate(Bundle savedInstanceState) {
    	super.onCreate(savedInstanceState);    	
    	
    	this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.storage_list);
        
        Button addButton = (Button)findViewById(R.id.header_add_button);
        Button backButton = (Button)findViewById(R.id.header_back_button);
        TextView title = (TextView)findViewById(R.id.header_title);
        
        addButton.setVisibility(View.VISIBLE);
        backButton.setVisibility(View.VISIBLE);

        Bundle optionSet = optionSet();  
        String text = optionSet.getString(StorageListActivity.TITLE_NAMESPACE);
        title.setText(text);
              
        listType = optionSet.getInt(TYPE_NAMESPACE);        
        ListView listView = (ListView)findViewById(R.id.storage_list_list_view);
        listView.setOnItemClickListener((OnItemClickListener) this);
        
        backButton.setOnClickListener(new View.OnClickListener( ) {
        	public void onClick(View view) { onBackButton(view); }
        });
        
        addButton.setOnClickListener(new View.OnClickListener( ) {
        	public void onClick(View view) { onAddButton(view); }
        });
    }

	public void onStart() {
		super.onStart();
        final StorageListActivity thisActivity = this;
       
		 class ListItemsTask extends AsyncTask<Integer, Integer, List<String>> {
		     protected List<String> doInBackground(Integer... listTypes) {
		    	List<String> listedItems = new ArrayList<String>();
		        try {
			        listedItems = new ArrayList<String>();
		        	switch(listType) {
			    		case LIST_TYPE_TABLE:
			    			// TODO: Plug with real table services
			    			listedItems = Arrays.asList(new String[] { "table-1",  "table-2", "table-3" });	 
			    			break;
			    		case LIST_TYPE_CONTAINER:
			    	        for (CloudBlobContainer container : thisActivity.getSampleApplication().getCloudBlobClient().listContainers())
			    	        {
			    	        	listedItems.add(container.getName());
			    	        }
			    			break;
			    			
			    		case LIST_TYPE_BLOB:
			    	        String containerName = thisActivity.getContainerListedName();
			    	        CloudBlobContainer container = thisActivity.getSampleApplication().getCloudBlobClient().getContainerReference(containerName);
			    	        for (CloudBlob blob : container.listBlobs("", true))
			    	        {
			    	        	listedItems.add(blob.getName());
			    	        }
			    			break;
			    		case LIST_TYPE_QUEUE:
			    	        for (CloudQueue queue : thisActivity.getSampleApplication().getCloudQueueClient().listQueues())
			    	        {
			    	        	listedItems.add(queue.getName());
			    	        }
			    			break;
		        	}
		        }
		        catch (Exception exception) {
		        	thisActivity.showErrorMessage("Couldn't execute the list operation", exception);
		        }
				return listedItems;
		     }

		     protected void onProgressUpdate(Integer... progress) {
					//scrollView.setVisibility(View.GONE);
		    	 //textView.setVisibility(View.GONE);
		    	 //progressBar.setVisibility(View.VISIBLE);
		     }

		     protected void onPostExecute(List<String> listedItems) {
		    	items = listedItems;
			    ListView listView = (ListView)findViewById(R.id.storage_list_list_view);
			    listView.setAdapter(new ArrayAdapter<String>(thisActivity, android.R.layout.simple_list_item_1, items));
		     }
		 };
		 new ListItemsTask().execute(listType);
	}

	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		Intent storageEntityListIntent = new Intent(this, StorageEntityListActivity.class);
		Intent storageListIntent = new Intent(this, StorageListActivity.class);
		Intent blobViewIntent = new Intent(this, StorageBlobViewActivity.class);
		
		try
		{
	    	switch (listType) {			
				case LIST_TYPE_TABLE:
					// TODO: Show table rows
					storageEntityListIntent.putExtra(StorageEntityListActivity.TYPE_NAMESPACE, StorageEntityListActivity.ENTITY_LIST_TYPE_TABLE);
					storageEntityListIntent.putExtra(StorageEntityListActivity.TITLE_NAMESPACE, items.get(arg2));
			    	startActivity (storageEntityListIntent);
					break;
				case LIST_TYPE_BLOB:
		    	    String containerName = this.optionSet().getString(StorageListActivity.TITLE_NAMESPACE);
					blobViewIntent.putExtra(StorageBlobViewActivity.CONTAINER_NAME_NAMESPACE, containerName);
					blobViewIntent.putExtra(StorageBlobViewActivity.BLOB_NAME_NAMESPACE, items.get(arg2));
					
			    	startActivity (blobViewIntent);		    	
					break;    			
				case LIST_TYPE_CONTAINER:				
					storageListIntent.putExtra(StorageListActivity.TYPE_NAMESPACE, StorageListActivity.LIST_TYPE_BLOB);
					storageListIntent.putExtra(StorageListActivity.TITLE_NAMESPACE, items.get(arg2));
			    	startActivity (storageListIntent);
			    	break;
				case LIST_TYPE_QUEUE:
					// TODO: Show queue messages
					storageEntityListIntent.putExtra(StorageEntityListActivity.TYPE_NAMESPACE, StorageEntityListActivity.ENTITY_LIST_TYPE_QUEUE);
					storageEntityListIntent.putExtra(StorageEntityListActivity.TITLE_NAMESPACE, items.get(arg2));
			    	startActivity (storageEntityListIntent);
					break;
			}
		}
        catch (Exception exception) {
        	this.showErrorMessage("Couldn't view the details of the item", exception);
        }
	}    
	
    private void onBackButton(View v) {
    	finish();
	}

    private void onAddButton(View v) {
    	Intent intent = new Intent(this, StorageCreateItemActivity.class);
    	
    	switch (listType){
    		case LIST_TYPE_TABLE:
    			intent.putExtra(StorageCreateItemActivity.TYPE_NAMESPACE, StorageCreateItemActivity.CREATE_ITEM_TYPE_TABLE);
    	    	intent.putExtra(StorageCreateItemActivity.TITLE_NAMESPACE, getString(R.string.create_table));
    	    	intent.putExtra(StorageCreateItemActivity.LABEL_TEXT_NAMESPACE, getString((R.string.create_table_label)));    	
    			break;
    		case LIST_TYPE_BLOB:
    	    	intent.putExtra(StorageCreateItemActivity.TYPE_NAMESPACE, StorageCreateItemActivity.CREATE_ITEM_TYPE_BLOB);
    	    	intent.putExtra(StorageCreateItemActivity.TITLE_NAMESPACE, getString(R.string.create_blob));
    	    	intent.putExtra(StorageCreateItemActivity.LABEL_TEXT_NAMESPACE, getString(R.string.create_blob_label));
    	    	intent.putExtra(StorageCreateItemActivity.CONTAINER_OR_QUEUE_NAME_NAMESPACE, this.getContainerListedName());
    			break;    			
    		case LIST_TYPE_CONTAINER:
    	    	intent.putExtra(StorageCreateItemActivity.TYPE_NAMESPACE, StorageCreateItemActivity.CREATE_ITEM_TYPE_CONTAINER);
    	    	intent.putExtra(StorageCreateItemActivity.TITLE_NAMESPACE, getString(R.string.create_container));
    	    	intent.putExtra(StorageCreateItemActivity.LABEL_TEXT_NAMESPACE, getString(R.string.create_container_label));
    			break;
    		case LIST_TYPE_QUEUE:
    	    	intent.putExtra(StorageCreateItemActivity.TYPE_NAMESPACE, StorageCreateItemActivity.CREATE_ITEM_TYPE_QUEUE);
    	    	intent.putExtra(StorageCreateItemActivity.TITLE_NAMESPACE, getString(R.string.create_queue));
    	    	intent.putExtra(StorageCreateItemActivity.LABEL_TEXT_NAMESPACE, getString(R.string.create_queue_label));
    			break;
    	}
    	
    	startActivity(intent);
	}

	private String getContainerListedName() {
		return this.optionSet().getString(StorageListActivity.TITLE_NAMESPACE);
	}

}
