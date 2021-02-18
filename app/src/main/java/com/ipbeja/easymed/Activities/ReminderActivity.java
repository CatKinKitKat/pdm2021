package com.ipbeja.easymed.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.view.ActionMode;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.RecyclerView.LayoutManager;

import com.amulyakhare.textdrawable.TextDrawable;
import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.bignerdranch.android.multiselector.ModalMultiSelectorCallback;
import com.bignerdranch.android.multiselector.MultiSelector;
import com.bignerdranch.android.multiselector.SwappingHolder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.ipbeja.easymed.Objects.Reminder;
import com.ipbeja.easymed.Objects.ReminderItem;
import com.ipbeja.easymed.R;
import com.ipbeja.easymed.SQLite.ReminderDatabase;
import com.ipbeja.easymed.Tools.AlarmReceiver;
import com.ipbeja.easymed.Tools.DateTimeComparator;
import com.ipbeja.easymed.Tools.DateTimeSorter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * The type Reminder activity.
 */
public class ReminderActivity extends AppCompatActivity {


    /**
     * The Dmap.
     */
    private final LinkedHashMap<Integer, Integer> IDmap = new LinkedHashMap<>();
    /**
     * The M multi selector.
     */
    private final MultiSelector mMultiSelector = new MultiSelector();
    /**
     * The M list.
     */
    private RecyclerView mList;
    /**
     * The M adapter.
     */
    private SimpleAdapter mAdapter;
    /**
     * The Rb.
     */
    private ReminderDatabase rb;
    /**
     * The M alarm receiver.
     */
    private AlarmReceiver mAlarmReceiver;
    /**
     * The M delete mode.
     */
    private final ActionMode.Callback mDeleteMode = new ModalMultiSelectorCallback(mMultiSelector) {
        @Override
        public boolean onCreateActionMode(ActionMode actionMode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_add_reminder, menu);
            return true;
        }

        @Override
        public boolean onActionItemClicked(ActionMode actionMode, MenuItem menuItem) {
            switch (menuItem.getItemId()) {
                case R.id.discard_reminder:
                    actionMode.finish();
                    for (int i = IDmap.size(); i >= 0; i--) {
                        if (mMultiSelector.isSelected(i, 0)) {
                            int id = IDmap.get(i);
                            Reminder temp = rb.getReminder(id);
                            rb.deleteReminder(temp);
                            mAdapter.removeItemSelected(i);
                            mAlarmReceiver.cancelAlarm(getApplicationContext(), id);
                        }
                    }
                    mMultiSelector.clearSelections();
                    mAdapter.onDeleteItem(getDefaultItemCount());
                    Toast.makeText(getApplicationContext(),
                            "Deleted",
                            Toast.LENGTH_SHORT).show();
                    List<Reminder> mTest = rb.getAllReminders();
                    if (mTest.isEmpty()) {
                        Toast.makeText(getApplicationContext(),
                                "You have no Meds to take!",
                                Toast.LENGTH_SHORT).show();
                    }
                    return true;
                case R.id.save_reminder:
                    actionMode.finish();
                    mMultiSelector.clearSelections();
                    return true;
                default:
                    break;
            }
            return false;
        }
    };

    /**
     * Gets default item count.
     *
     * @return the default item count
     */
    public static int getDefaultItemCount() {
        return 100;
    }

    /**
     * Select reminder.
     *
     * @param mClickID the m click id
     */
    public void selectReminder(int mClickID) {
        String mStringClickID = Integer.toString(mClickID);

        Intent i = new Intent(this, ReminderEditActivity.class);
        i.putExtra(ReminderEditActivity.EXTRA_REMINDER_ID, mStringClickID);
        startActivityForResult(i, 1);
    }

    /**
     * On create.
     *
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(getString(R.string.reminder_title));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        this.rb = new ReminderDatabase(getApplicationContext());


        FloatingActionButton mAddReminderButton = findViewById(R.id.add_reminder);
        this.mList = findViewById(R.id.reminder_list);

        List<Reminder> mTest = rb.getAllReminders();

        if (mTest.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "You have no Meds to take!",
                    Toast.LENGTH_SHORT).show();
        }

        this.mList.setLayoutManager(getLayoutManager());
        registerForContextMenu(this.mList);
        this.mAdapter = new SimpleAdapter();
        this.mAdapter.setItemCount(getDefaultItemCount());
        this.mList.setAdapter(this.mAdapter);


        mAddReminderButton.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), ReminderAddActivity.class);
            startActivity(intent);
        });


        this.mAlarmReceiver = new AlarmReceiver();
    }

    /**
     * On options item selected boolean.
     *
     * @param item the item
     * @return the boolean
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * On resume.
     */
    @Override
    public void onResume() {
        super.onResume();

        List<Reminder> mTest = this.rb.getAllReminders();

        if (mTest.isEmpty()) {
            Toast.makeText(getApplicationContext(),
                    "You have no Meds to take!",
                    Toast.LENGTH_SHORT).show();
        }

        this.mAdapter.setItemCount(getDefaultItemCount());
    }

    /**
     * Gets layout manager.
     *
     * @return the layout manager
     */
    protected LayoutManager getLayoutManager() {
        return new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
    }

    /**
     * The type Simple adapter.
     */
    public class SimpleAdapter extends RecyclerView.Adapter<SimpleAdapter.VerticalItemHolder> {
        /**
         * The M items.
         */
        private final ArrayList<ReminderItem> mItems;

        /**
         * Instantiates a new Simple adapter.
         */
        public SimpleAdapter() {
            this.mItems = new ArrayList<>();
        }

        /**
         * On delete item.
         *
         * @param count the count
         */
        public void onDeleteItem(int count) {
            this.mItems.clear();
            this.mItems.addAll(generateData(count));
        }

        /**
         * Remove item selected.
         *
         * @param selected the selected
         */
        public void removeItemSelected(int selected) {
            if (this.mItems.isEmpty()) return;
            this.mItems.remove(selected);
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
            View root = inflater.inflate(R.layout.reminder_recycleview, container, false);

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
            ReminderItem item = this.mItems.get(position);
            itemHolder.setReminderTitle(item.mTitle);
            itemHolder.setReminderDateTime(item.mDateTime);
            itemHolder.setReminderRepeatInfo(item.mRepeat, item.mRepeatNo, item.mRepeatType);
            itemHolder.setActiveImage(item.mActive);
        }

        /**
         * Gets item count.
         *
         * @return the item count
         */
        @Override
        public int getItemCount() {
            return this.mItems.size();
        }

        /**
         * Sets item count.
         *
         * @param count the count
         */
        public void setItemCount(int count) {
            this.mItems.clear();
            this.mItems.addAll(generateData(count));
            notifyDataSetChanged();
        }

        /**
         * Generate data list.
         *
         * @param count the count
         * @return the list
         */
        public List<ReminderItem> generateData(int count) {
            ArrayList<ReminderItem> items = new ArrayList<>();


            List<Reminder> reminders = rb.getAllReminders();


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

                items.add(new ReminderItem(Titles.get(i), DateAndTime.get(i), Repeats.get(i),
                        RepeatNos.get(i), RepeatTypes.get(i), Actives.get(i)));
                IDmap.put(k, IDList.get(i));
                k++;
            }
            return items;
        }

        /**
         * The type Vertical item holder.
         */
        public class VerticalItemHolder extends SwappingHolder
                implements View.OnClickListener, View.OnLongClickListener {
            /**
             * The M title text.
             */
            private final TextView mTitleText;
            /**
             * The M date and time text.
             */
            private final TextView mDateAndTimeText;
            /**
             * The M repeat info text.
             */
            private final TextView mRepeatInfoText;
            /**
             * The M active image.
             */
            private final ImageView mActiveImage;
            /**
             * The M thumbnail image.
             */
            private final ImageView mThumbnailImage;
            /**
             * The M color generator.
             */
            private final ColorGenerator mColorGenerator = ColorGenerator.DEFAULT;
            /**
             * The M adapter.
             */
            private final SimpleAdapter mAdapter;

            /**
             * Instantiates a new Vertical item holder.
             *
             * @param itemView the item view
             * @param adapter  the adapter
             */
            public VerticalItemHolder(View itemView, SimpleAdapter adapter) {
                super(itemView, mMultiSelector);
                itemView.setOnClickListener(this);
                itemView.setOnLongClickListener(this);
                itemView.setLongClickable(true);

                this.mAdapter = adapter;

                this.mTitleText = itemView.findViewById(R.id.recycle_title);
                this.mDateAndTimeText = itemView.findViewById(R.id.recycle_date_time);
                this.mRepeatInfoText = itemView.findViewById(R.id.recycle_repeat_info);
                this.mActiveImage = itemView.findViewById(R.id.active_image);
                this.mThumbnailImage = itemView.findViewById(R.id.thumbnail_image);
            }


            /**
             * On click.
             *
             * @param v the v
             */
            @Override
            public void onClick(View v) {
                if (!mMultiSelector.tapSelection(this)) {
                    /**
                     * The M temp post.
                     */
                    int mTempPost = mList.getChildAdapterPosition(v);

                    int mReminderClickID = IDmap.get(mTempPost);
                    selectReminder(mReminderClickID);

                } else if (mMultiSelector.getSelectedPositions().isEmpty()) {
                    this.mAdapter.setItemCount(getDefaultItemCount());
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
                activity.startSupportActionMode(mDeleteMode);
                mMultiSelector.setSelected(this, true);
                return true;
            }


            /**
             * Sets reminder title.
             *
             * @param title the title
             */
            public void setReminderTitle(String title) {
                this.mTitleText.setText(title);
                String letter = "A";

                if (title != null && !title.isEmpty()) {
                    letter = title.substring(0, 1);
                }

                int color = this.mColorGenerator.getRandomColor();
                TextDrawable mDrawableBuilder = TextDrawable.builder()
                        .buildRound(letter, color);
                this.mThumbnailImage.setImageDrawable(mDrawableBuilder);
            }


            /**
             * Sets reminder date time.
             *
             * @param datetime the datetime
             */
            public void setReminderDateTime(String datetime) {
                this.mDateAndTimeText.setText(datetime);
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
                    this.mRepeatInfoText.setText("Every " + repeatNo + " " + repeatType + "(s)");
                } else if (repeat.equals("false")) {
                    this.mRepeatInfoText.setText("Repeat Off");
                }
            }


            /**
             * Sets active image.
             *
             * @param active the active
             */
            public void setActiveImage(String active) {
                if (active.equals("true")) {
                    this.mActiveImage.setImageResource(R.drawable.notificate);
                } else if (active.equals("false")) {
                    this.mActiveImage.setImageResource(R.drawable.mute);
                }
            }
        }
    }

}