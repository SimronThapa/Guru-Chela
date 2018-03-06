package com.kec.guruchela;



import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
 
public class ItemListBaseAdapter extends BaseAdapter {
 private static ArrayList<ItemDetails> itemDetailsrrayList;
  
 /*private Integer[] imgid = {
   R.drawable.hyattregency,
   R.drawable.sangri,
   R.drawable.radission,
   R.drawable.crown,
   R.drawable.shanker
     };*/
  
 private LayoutInflater l_Inflater;
 
 public ItemListBaseAdapter(Context context, ArrayList<ItemDetails> results) {
  itemDetailsrrayList = results;
  l_Inflater = LayoutInflater.from(context);
 }
 
 public int getCount() {
  return itemDetailsrrayList.size();
 }
 
 public Object getItem(int position) {
  return itemDetailsrrayList.get(position);
 }
 
 public long getItemId(int position) {
  return position;
 }
 
 public View getView(int position, View convertView, ViewGroup parent) {
  ViewHolder holder;
  if (convertView == null) {
   convertView = l_Inflater.inflate(R.layout.list_row, null);
   holder = new ViewHolder();
   holder.txt_itemName1 = (TextView) convertView.findViewById(R.id.tvAll1);
   holder.txt_itemName = (TextView) convertView.findViewById(R.id.tvAll);
   holder.txt_itemDescription = (TextView) convertView.findViewById(R.id.b5);
   
   //holder.itemImage = (ImageView) convertView.findViewById(R.id.imageView1);
 
   convertView.setTag(holder);
  } else {
   holder = (ViewHolder) convertView.getTag();
  }
  holder.txt_itemName.setText(itemDetailsrrayList.get(position).getUname()); 
  holder.txt_itemDescription.setText(itemDetailsrrayList.get(position).getDescription());
  holder.txt_itemName1.setText(itemDetailsrrayList.get(position).getShareditem());

  //holder.itemImage.setImageResource(imgid[itemDetailsrrayList.get(position).getImageNumber() - 1]);
 
  return convertView;
 }
 
 static class ViewHolder {
  TextView txt_itemName;
  TextView txt_itemName1;
  TextView txt_itemDescription;
  
  //ImageView itemImage;
 }
}