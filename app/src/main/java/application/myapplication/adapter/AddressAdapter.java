package application.myapplication.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import application.myapplication.R;

public class AddressAdapter extends ArrayAdapter {
    private int layoutId;
    private Context mContext;
    public static String[] names = new String[]{"张三", "李四", "王二", "李老四", "张老三", "王老五", "钻石王老五", "迪丽热巴", "范冰冰", "刘亦菲"};


    public AddressAdapter(@NonNull Context context, int resource, String[] data) {
        super(context, resource,data);
        this.mContext = context;
        this.layoutId = resource;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View view;
        AddressHolder addressHolder;
        if (convertView != null) {
            view = convertView;
            addressHolder = (AddressHolder) view.getTag();
        } else {
            view = LayoutInflater.from(mContext).inflate(layoutId, parent, false);
            addressHolder = new AddressHolder();
            addressHolder.addressImage = view.findViewById(R.id.img_address_avatar);
            addressHolder.addressName = view.findViewById(R.id.txt_address_name);
            view.setTag(addressHolder);
        }
        addressHolder.addressImage.setImageResource(R.drawable.account);
        addressHolder.addressName.setText(names[position]);
        return view;
    }

    class AddressHolder {
        ImageView addressImage;
        TextView addressName;
    }

}
