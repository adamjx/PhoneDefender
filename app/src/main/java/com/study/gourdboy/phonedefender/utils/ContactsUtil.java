package com.study.gourdboy.phonedefender.utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;

import com.study.gourdboy.phonedefender.bean.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by gourdboy on 2016/3/31.
 */
public class ContactsUtil
{
        public static List<Contact> getAllContact(Context ctx)
    {
        List<Contact> contactList = new ArrayList<>();
        ContentResolver contentResolver = ctx.getContentResolver();
        Cursor cursor = contentResolver.query(Uri.parse("content://com.android.contacts/raw_contacts"),
                new String[]{"contact_id"},null,null,null);
        while(cursor.moveToNext())
        {
            int contact_id = cursor.getInt(0);
            if(contact_id==0)
            {
                continue;
            }
            Cursor cursor1 = contentResolver.query(Uri.parse("content://com.android.contacts/data"),
                    new String[]{"data1","mimetype"},"raw_contact_id=?",new String[]{""+contact_id},null);
            Contact contact = new Contact();
            while(cursor1.moveToNext())
            {
                String data = cursor1.getString(0);
                String mimetype = cursor1.getString(1);
                if("vnd.android.cursor.item/name".equals(mimetype))
                {
                    contact.setName(data);
                }
                else if("vnd.android.cursor.item/phone_v2".equals(mimetype))
                {
                    contact.setNumber(data);
                }
            }
            contactList.add(contact);
        }
        return contactList;
    }
}
