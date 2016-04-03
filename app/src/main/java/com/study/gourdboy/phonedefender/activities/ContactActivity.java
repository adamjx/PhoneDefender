package com.study.gourdboy.phonedefender.activities;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.study.gourdboy.phonedefender.R;
import com.study.gourdboy.phonedefender.bean.Contact;
import com.study.gourdboy.phonedefender.utils.ContactsUtil;

import java.util.ArrayList;
import java.util.List;

public class ContactActivity extends ActionBarActivity
{
    private ListView lv_contactlist_showcontact;
    private List<Contact> contactList;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        lv_contactlist_showcontact = (ListView) findViewById(R.id.lv_contactlist_showcontact);
        contactList = new ArrayList();
        contactList = ContactsUtil.getAllContact(this);
        lv_contactlist_showcontact.setAdapter(new MyContactAdapter());
        lv_contactlist_showcontact.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Contact contact = contactList.get(position);
                Intent data = new Intent();
                data.putExtra("number",contact.getNumber());
                setResult(1000,data);
                finish();
            }
        });
    }
    class MyContactAdapter extends BaseAdapter
    {
        @Override
        public int getCount()
        {
            return contactList.size();
        }

        @Override
        public Object getItem(int position)
        {
            return null;
        }

        @Override
        public long getItemId(int position)
        {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            Contact contact = contactList.get(position);
            View v = View.inflate(ContactActivity.this, R.layout.item_contactlist, null);
            TextView tv_contactlist_name = (TextView) v.findViewById(R.id.tv_contactlist_name);
            TextView tv_contactlist_number = (TextView) v.findViewById(R.id.tv_contactlist_number);
            tv_contactlist_name.setText(contact.getName());
            tv_contactlist_number.setText(contact.getNumber());
            return v;
        }
    }

}
