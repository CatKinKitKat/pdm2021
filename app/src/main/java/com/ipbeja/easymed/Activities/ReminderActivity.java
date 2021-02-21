package com.ipbeja.easymed.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ipbeja.easymed.Alarm.DataBase.ReminderDatabase;
import com.ipbeja.easymed.Alarm.Receivers.AlarmReceiver;
import com.ipbeja.easymed.Alarm.Tools.DateTimeSorter;
import com.ipbeja.easymed.Objects.Reminder;
import com.ipbeja.easymed.R;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The type Reminder activity.
 */
public class ReminderActivity extends AppCompatActivity {
    /**
     * The Id map.
     */
    private final LinkedHashMap<Integer, Integer> idMap = new LinkedHashMap<>();
    /**
     * The Long press mode.
     */
    private final MultiSelector longPressMode = new MultiSelector();
    /**
     * The Reminder list.
     */
    private RecyclerView reminderList;
    /**
     * The Reminder recycle view adapter.
     */
    private ReminderRecycleViewAdapter reminderRecycleViewAdapter;
    /**
     * The Reminder database.
     */
    private ReminderDatabase reminderDatabase;
    /**
     * The Alarm receiver.
     */
    private AlarmReceiver alarmReceiver;
    /**
     * The Delete mode.
     */
    private final ActionMode.Callback deleteMode = new ModalMultiSelectorCallback(longPressMode) {

        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_rem_reminder, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            if (menuItem.getItemId() == R.id.delete_reminder) {
                for (int i = idMap.size(); i >= 0; i--) {
                    if (longPressMode.isSelected(i, 0)) {
                        Integer id = idMap.get(i);
                        Reminder temp = reminderDatabase.getReminder(id);
                        reminderDatabase.deleteReminder(temp);
                        reminderRecycleViewAdapter.removeItemSelected(i);
                        alarmReceiver.cancelAlarm(getApplicationContext(), id);
                    }
                }
                longPressMode.clearSelections();
                reminderRecycleViewAdapter.onDeleteItem(getDefaultItemCount());
                reminderDatabase.getAllReminders();
                actionMode.finish();
                return true;
            }
            return false;
        }
    };

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);


        this.reminderDatabase = new ReminderDatabase(getApplicationContext());


        FloatingActionButton mAddReminderButton = findViewById(R.id.fab);
        this.reminderList = findViewById(R.id.alarm_list);


        this.reminderDatabase.getAllReminders();


        this.reminderList.setLayoutManager(getLayoutManager());
        registerForContextMenu(this.reminderList);
        this.reminderRecycleViewAdapter = new ReminderRecycleViewAdapter();
        this.reminderRecycleViewAdapter.setItemCount(getDefaultItemCount());
        this.reminderList.setAdapter(this.reminderRecycleViewAdapter);

        Toolbar toolbar = findViewById(R.id.toolbar);

        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.reminder_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        mAddReminderButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ReminderAddActivity.class);
            startActivity(intent);
        });
        this.alarmReceiver = new AlarmReceiver();
    }

    /**
     * On create context menu.
     *
     * @param menu     the menu
     * @param v        the v
     * @param menuInfo the menu info
     */
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
    }

    /**
     * Select reminder.
     *
     * @param mClickID the m click id
     */
    private void selectReminder(int mClickID) {
        String mStringClickID = Integer.toString(mClickID);


        Intent i = new Intent(this, ReminderEditActivity.class);
        i.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, mStringClickID);
        startActivityForResult(i, 1);
    }

    /**
     * On activity result.
     *
     * @param requestCode the request code
     * @param resultCode  the result code
     * @param data        the data
     */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        this.reminderRecycleViewAdapter.setItemCount(getDefaultItemCount());
    }


    /**
     * On resume.
     */
    @Override
    public void onResume() {
        super.onResume();


        this.reminderDatabase.getAllReminders();


        this.reminderRecycleViewAdapter.setItemCount(getDefaultItemCount());
    }


    /**
     * Gets layout manager.
     *
     * @return the layout manager
     */
    protected RecyclerView.LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    /**
     * Gets default item count.
     *
     * @return the default item count
     */
    protected int getDefaultItemCount() {
        return 100;
    }


    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    /**
     * The type Reminder recycle view adapter.
     */
    public class ReminderRecycleViewAdapter extends RecyclerView.Adapter<ReminderRecycleViewAdapter.VerticalItemHolder> {
        /**
         * The Reminder items.
         */
        private final ArrayList<ReminderItem> reminderItems;

        /**
         * Instantiates a new Reminder recycle view adapter.
         */
        public ReminderRecycleViewAdapter() {
            this.reminderItems = new ArrayList<>();
        }

        /**
         * On delete item.
         *
         * @param count the count
         */
        public void onDeleteItem(int count) {
            this.reminderItems.clear();
            this.reminderItems.addAll(generateData(count));
        }

        /**
         * Remove item selected.
         *
         * @param selected the selected
         */
        public void removeItemSelected(int selected) {
            if (reminderItems.isEmpty()) return;
            this.reminderItems.remove(selected);
            notifyItemRemoved(selected);
        }

        /**
         * On create view holder vertical item holder.
         *
         * @param container the container
         * @param viewType  the view type
         * @return the vertical item holder
         */
        @Override
        public VerticalItemHolder onCreateViewHolder(ViewGroup container, int viewType) {

            LayoutInflater inflater = LayoutInflater.from(container.getContext());
            View root = inflater.inflate(R.layout.reminder_item, container, false);

            return new VerticalItemHolder(root, this);
        }

        /**
         * On bind view holder.
         *
         * @param itemHolder the item holder
         * @param position   the position
         */
        @Override
        public void onBindViewHolder(VerticalItemHolder itemHolder, int position) {
            ReminderItem item = reminderItems.get(position);
            itemHolder.setReminderTitle(item.reminderTitle);
            itemHolder.setReminderDateTime(item.dateTime);
            itemHolder.setReminderRepeatInfo(item.repeat, item.repeatNo, item.repeatType);
            itemHolder.setActiveImage(item.activeImage);
        }

        /**
         * Gets item count.
         *
         * @return the item count
         */
        @Override
        public int getItemCount() {
            return reminderItems.size();
        }

        /**
         * Sets item count.
         *
         * @param count the count
         */
        public void setItemCount(int count) {
            this.reminderItems.clear();
            this.reminderItems.addAll(generateData(count));
            notifyDataSetChanged();
        }

        /**
         * Generate dummy data reminder item.
         *
         * @return the reminder item
         */
        public ReminderItem generateDummyData() {
            return new ReminderItem("1", "2", "3", "4", "5", "6");
        }

        /**
         * Generate data list.
         *
         * @param count the count
         * @return the list
         */
        public List<ReminderItem> generateData(int count) {
            ArrayList<ReminderRecycleViewAdapter.ReminderItem> items = new ArrayList<>();


            List<Reminder> reminders = reminderDatabase.getAllReminders();


            List<String> Titles = new ArrayList<>();
            List<String> Repeats = new ArrayList<>();
            List<String> RepeatNos = new ArrayList<>();
            List<String> RepeatTypes = new ArrayList<>();
            List<String> Actives = new ArrayList<>();
            List<String> DateAndTime = new ArrayList<>();
            List<Integer> IDList = new ArrayList<>();
            List<DateTimeSorter> DateTimeSortList = new ArrayList<>();


            for (Reminder r : reminders) {
                Titles.add(r.getTitle());
                DateAndTime.add(r.getDate() + " " + r.getTime());
                Repeats.add(r.getRepeat());
                RepeatNos.add(r.getRepeatNo());
                RepeatTypes.add(r.getRepeatType());
                Actives.add(r.getActive());
                IDList.add(r.getID());
            }

            int key = 0;


            for (int k = 0; k < Titles.size(); k++) {
                DateTimeSortList.add(new DateTimeSorter(key, DateAndTime.get(k)));
                key++;
            }


            Collections.sort(DateTimeSortList, new DateTimeComparator());

            int k = 0;


            for (DateTimeSorter item : DateTimeSortList) {
                int i = item.getIndex();

                items.add(new ReminderRecycleViewAdapter.ReminderItem(Titles.get(i), DateAndTime.get(i), Repeats.get(i),
                        RepeatNos.get(i), RepeatTypes.get(i), Actives.get(i)));
                idMap.put(k, IDList.get(i));
                k++;
            }
            return items;
        }

        /**
         * The type Reminder item.
         */
        public class ReminderItem {
            /**
             * The Reminder title.
             */
            public String reminderTitle;
            /**
             * The Date time.
             */
            public String dateTime;
            /**
             * The Repeat.
             */
            public String repeat;
            /**
             * The Repeat no.
             */
            public String repeatNo;
            /**
             * The Repeat type.
             */
            public String repeatType;
            /**
             * The Active image.
             */
            public String activeImage;

            /**
             * Instantiates a new Reminder item.
             *
             * @param Title      the title
             * @param DateTime   the date time
             * @param Repeat     the repeat
             * @param RepeatNo   the repeat no
             * @param RepeatType the repeat type
             * @param Active     the active
             */
            public ReminderItem(String Title, String DateTime, String Repeat, String RepeatNo, String RepeatType, String Active) {
                this.reminderTitle = Title;
                this.dateTime = DateTime;
                this.repeat = Repeat;
                this.repeatNo = RepeatNo;
                this.repeatType = RepeatType;
                this.activeImage = Active;
            }
        }

        /**
         * The type Date time comparator.
         */
        public class DateTimeComparator implements Comparator {
            /**
             * The F.
             */
            DateFormat f = new SimpleDateFormat("dd/mm/yyyy hh:mm");

            /**
             * Compare int.
             *
             * @param a the a
             * @param b the b
             * @return the int
             */
            public int compare(Object a, Object b) {
                String o1 = ((DateTimeSorter) a).getDateTime();
                String o2 = ((DateTimeSorter) b).getDateTime();

                try {
                    return f.parse(o1).compareTo(f.parse(o2));
                } catch (ParseException e) {
                    throw new IllegalArgumentException(e);
                }
            }
        }

        /**
         * The type Vertical item holder.
         */
        public class VerticalItemHolder extends SwappingHolder
                implements View.OnClickListener, View.OnLongClickListener {
            /**
             * The Title text.
             */
            private final TextView titleText;
            /**
             * The Date and time text.
             */
            private final TextView dateAndTimeText;
            /**
             * The Repeat info text.
             */
            private final TextView repeatInfoText;
            /**
             * The Active image.
             */
            private final ImageView activeImage;
            /**
             * The Thumbnail image.
             */
            private final ImageView thumbnailImage;
            /**
             * The Color generator.
             */
            private final ColorGenerator colorGenerator = ColorGenerator.DEFAULT;
            /**
             * The Recycle view adapter.
             */
            private final ReminderRecycleViewAdapter recycleViewAdapter;

            /**
             * Instantiates a new Vertical item holder.
             *
             * @param itemView the item view
             * @param adapter  the adapter
             */
            public VerticalItemHolder(View itemView, ReminderRecycleViewAdapter adapter) {
                super(itemView, longPressMode);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
                itemView.setLongClickable(true);


                this.recycleViewAdapter = adapter;


                this.titleText = itemView.findViewById(R.id.recycle_title);
                this.dateAndTimeText = itemView.findViewById(R.id.recycle_date_time);
                this.repeatInfoText = itemView.findViewById(R.id.recycle_repeat_info);
                this.activeImage = itemView.findViewById(R.id.active_image);
                this.thumbnailImage = itemView.findViewById(R.id.thumbnail_image);
            }


            /**
             * On click.
             *
             * @param v the v
             */
            @Override
            public void onClick(View v) {
                if (!longPressMode.tapSelection(this)) {
                    int mTempPost = reminderList.getChildAdapterPosition(v);

                    Integer mReminderClickID = idMap.get(mTempPost);
                    selectReminder(mReminderClickID);

                } else if (longPressMode.getSelectedPositions().isEmpty()) {
                    this.recycleViewAdapter.setItemCount(getDefaultItemCount());
                }
            }


            /**
             * On long click boolean.
             *
             * @param v the v
             * @return the boolean
             */
            @Override
            public boolean onLongClick(View v) {
                AppCompatActivity activity = ReminderActivity.this;
                activity.startSupportActionMode(deleteMode);
                longPressMode.setSelected(this, true);
                return true;
            }


            /**
             * Sets reminder title.
             *
             * @param title the title
             */
            public void setReminderTitle(String title) {
                this.titleText.setText(title);
                String letter = "A";

                if (title != null && !title.isEmpty()) {
                    letter = title.substring(0, 1);
                }

                int color = colorGenerator.getRandomColor();


                TextDrawable textDrawable = TextDrawable.builder()
                        .buildRound(letter, color);
                this.thumbnailImage.setImageDrawable(textDrawable);
            }


            /**
             * Sets reminder date time.
             *
             * @param datetime the datetime
             */
            public void setReminderDateTime(String datetime) {
                this.dateAndTimeText.setText(datetime);
            }


            /**
             * Sets reminder repeat info.
             *
             * @param repeat     the repeat
             * @param repeatNo   the repeat no
             * @param repeatType the repeat type
             */
            public void setReminderRepeatInfo(String repeat, String repeatNo, String repeatType) {
                if (repeat.equals("true")) {
                    this.repeatInfoText.setText("Every " + repeatNo + " " + repeatType + "(s)");
                } else if (repeat.equals("false")) {
                    this.repeatInfoText.setText(getString(R.string.repeat_off));
                }
            }


            /**
             * Sets active image.
             *
             * @param active the active
             */
            public void setActiveImage(String active) {
                if (active.equals("true")) {
                    this.activeImage.setImageResource(R.drawable.notificate);
                } else if (active.equals("false")) {
                    this.activeImage.setImageResource(R.drawable.mute);
                }
            }
        }
    }
}