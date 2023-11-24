package com.project.hairtologyuser.views.fragments.reserve;

import static com.project.hairtologyuser.views.activities.MainActivity.containerViewId;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.google.firebase.database.DatabaseError;
import com.google.gson.Gson;
import com.project.hairtologyuser.R;
import com.project.hairtologyuser.components.utils.ErrorUtil;
import com.project.hairtologyuser.components.utils.StringFormat;
import com.project.hairtologyuser.components.utils.ToastMessage;
import com.project.hairtologyuser.models.ReservationModel;
import com.project.hairtologyuser.models.ShopDetail;
import com.project.hairtologyuser.models.ShopModel;
import com.project.hairtologyuser.models.ShopReview;
import com.project.hairtologyuser.models.ShopService;
import com.project.hairtologyuser.views.activities.MainActivity;
import com.project.hairtologyuser.views.fragments.map.MapFragment;
import com.project.hairtologyuser.views.fragments.servicelist.ServiceListAdapter;
import com.project.hairtologyuser.views.fragments.shop.ShopFragment;
import com.project.hairtologyuser.views.fragments.shoplistimage.ShopListImageAdapter;
import com.project.hairtologyuser.views.fragments.shopreview.ShopReviewFragment;

import org.json.JSONException;
import org.json.JSONObject;

import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ReserveFragment extends Fragment {

    private enum PageType {
        SHOP,
        RESERVATION
    }

    private enum ActionType {
        RESET,
        SUBMIT
    }

    private static ShopModel mShop;
    private ReserveViewModel mViewModel;
    private ShopDetail mShopDetail;
    private LinearLayout mServiceLoadingLinearLayout;
    private LinearLayout mShopLinearLayout;
    private LinearLayout mReserveLinearLayout;
    private LinearLayout mReadReviewLinearLayout;
    private CardView mDateCardView;
    private CardView mTimeCardView;
    private ShopListImageAdapter imageAdapter;
    private ServiceListAdapter mServiceAdapter;
    private ViewPager mViewPager;
    private TextView mShopName;
    private TextView mShopDescription;
    private TextView mAddress;
    private TextView mHours;
    private TextView mServiceDetail;
    private TextView mSelectedDateTextView;
    private TextView mSelectedTimeTextView;
    private TextView mRatingReviewTextView;
    private ImageView mFavorite;
    private ImageView mMap;
    private ImageView mStar1ImageView;
    private ImageView mStar2ImageView;
    private ImageView mStar3ImageView;
    private ImageView mStar4ImageView;
    private ImageView mStar5ImageView;
    private Button mCancelButton;
    private Button mSubmitButton;
    private ProgressBar mSubmitProgressBar;
    private final ArrayList<String> mShopImageArrayList = new ArrayList<>();
    private final ArrayList<ShopService> mServiceArrayList = new ArrayList<>();
    private String mTime = "";
    private String mMinute = "";
    private String mMeridian = "";
    private String mDay = "";
    private String mMonth = "";
    private String mYear = "";

    public static ReserveFragment newInstance(ShopModel shop) {
        mShop = shop;
        return new ReserveFragment();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_reserve, container, false);

        mServiceLoadingLinearLayout = view.findViewById(R.id.serviceLoadingLinearLayout);
        mShopLinearLayout = view.findViewById(R.id.reserveShopLinearLayout);
        mReserveLinearLayout = view.findViewById(R.id.reserveLinearLayout);
        mReadReviewLinearLayout = view.findViewById(R.id.reserveReadReviewLinearLayout);
        mDateCardView = view.findViewById(R.id.reserveDateCardView);
        mTimeCardView = view.findViewById(R.id.reserveTimeCardView);
        mViewPager = view.findViewById(R.id.reserveViewPager);
        mShopName = view.findViewById(R.id.reserveShopName);
        mShopDescription = view.findViewById(R.id.reserveShopDescription);
        mAddress = view.findViewById(R.id.reserveAddress);
        mHours = view.findViewById(R.id.reserveHours);
        mServiceDetail = view.findViewById(R.id.reserveServiceDetail);
        mSelectedDateTextView = view.findViewById(R.id.reserveDateTextView);
        mSelectedTimeTextView = view.findViewById(R.id.reserveTimeTextView);
        mRatingReviewTextView = view.findViewById(R.id.reserveReviewTextView);
        mFavorite = view.findViewById(R.id.reserveFavoriteImageView);
        mMap = view.findViewById(R.id.reserveMapImageView);
        mCancelButton = view.findViewById(R.id.reserveCancelButton);
        mSubmitButton = view.findViewById(R.id.reserveSubmitButton);
        mSubmitProgressBar = view.findViewById(R.id.reserveSubmitProgressBar);
        mStar1ImageView = view.findViewById(R.id.reserveStar1ImageView);
        mStar2ImageView = view.findViewById(R.id.reserveStar2ImageView);
        mStar3ImageView = view.findViewById(R.id.reserveStar3ImageView);
        mStar4ImageView = view.findViewById(R.id.reserveStar4ImageView);
        mStar5ImageView = view.findViewById(R.id.reserveStar5ImageView);

        mShopDetail = mShop.getShopDetail();
        mShopName.setText(mShopDetail.getName());
        mShopDescription.setText(mShopDetail.getDescription());
        mAddress.setText(mShopDetail.getAddress());
        mHours.setText(mShopDetail.getHour());
        mShopImageArrayList.add(mShopDetail.getImageId1());
        mShopImageArrayList.add(mShopDetail.getImageId2());
        mShopImageArrayList.add(mShopDetail.getImageId3());

        imageAdapter = new ShopListImageAdapter(getContext(), mShopDetail.getUuid(), mShopImageArrayList);
        mViewPager.setPadding(25, 0, 25, 0);
        mViewPager.setAdapter(imageAdapter);

        mServiceAdapter = new ServiceListAdapter(view.getContext(), mShopDetail, mServiceArrayList);

        RecyclerView recyclerView = view.findViewById(R.id.reserveServiceListItem);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(mServiceAdapter);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mViewModel = new ViewModelProvider(this).get(ReserveViewModel.class);
        mViewModel.setViewModel(requireActivity().getApplication());
        mViewModel.setReviewArrayList(mShop.getReview());

        mViewModel.service(mShopDetail.getUuid(), new ReserveViewModel.OnServiceDataListener() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onSuccess(ArrayList<ShopService> serviceList) {
                mServiceArrayList.addAll(serviceList);
                mServiceAdapter.notifyDataSetChanged();
                mServiceLoadingLinearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onFailed(DatabaseError error) {
                ToastMessage.display(getContext(), "Error: " + error);
            }
        });

        mServiceAdapter.setOnServiceListListener((position, service) -> {
            mViewModel.setShop(mShopDetail);
            mViewModel.setService(service);
            mServiceDetail.setText(service.getDescription());
            displayPage(PageType.RESERVATION);
        });

        mFavorite.setOnClickListener(v -> {
            mViewModel.favorite(mShopDetail.getUuid(), new ReserveViewModel.OnFavoriteTapListener() {
                @Override
                public void onSuccess() {
                    ToastMessage.display(getContext(), "Successfully added as favorite");
                }

                @Override
                public void onFailed(Exception exception) {
                    ToastMessage.display(getContext(), "Error: " + exception.getMessage());
                }
            });
        });

        mReadReviewLinearLayout.setOnClickListener(v -> onReadReview());

        mMap.setOnClickListener(v -> {
            if (getActivity() == null) {
                ToastMessage.display(getContext(), ErrorUtil.getErrorMessage(
                        ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                        ReserveFragment.class
                ));
                return;
            }

            Gson gson = new Gson();
            String jsonString = gson.toJson(mShopDetail);

            Bundle bundle = new Bundle();
            bundle.putString("data", jsonString);

            Fragment fragment = new MapFragment();
            fragment.setArguments(bundle);

            ((MainActivity) getActivity()).replaceFragment(
                    fragment,
                    MainActivity.containerViewId);
        });

        mDateCardView.setOnClickListener(v -> {
            selectDate();
        });

        mTimeCardView.setOnClickListener(v -> {
            selectTime();
        });

        mCancelButton.setOnClickListener(v -> {
            if (mShopDetail != null) {
                mShopDescription.setText(mShopDetail.getDescription());
            }
            displayPage(PageType.SHOP);
            setAction(ActionType.RESET);
        });

        mSubmitButton.setOnClickListener(v -> {
            setAction(ActionType.SUBMIT);

            if (mTime.isEmpty() || mDay.isEmpty() || mMonth.isEmpty()) {
                setAction(ActionType.RESET);
                ToastMessage.display(getContext(), "Please provide all information needed");
                return;
            }

            mViewModel.reserve(mTime, mMinute, mMeridian, mDay, mMonth, mYear, new ReserveViewModel.OnReserveTapListener() {
                @Override
                public void onSuccess(ReservationModel reservation) {
                    setAction(ActionType.RESET);

                    if (getActivity() == null) {
                        ToastMessage.display(getContext(), ErrorUtil.getErrorMessage(
                                ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                                ReserveFragment.class
                        ));
                        return;
                    }

                    ((MainActivity) getActivity()).replaceFragment(
                            new ShopFragment(),
                            containerViewId);

                    ToastMessage.display(getContext(), "Successfully added a reservation");
                }

                @Override
                public void onFailed(Throwable throwable) {
                    setAction(ActionType.RESET);
                    ToastMessage.display(getContext(), "Error: " + throwable.getCause());
                }

                @Override
                public void onFailed(DatabaseError error) {
                    setAction(ActionType.RESET);
                    ToastMessage.display(getContext(), "Error: " + error.getMessage());
                }
            });
        });

        mServiceLoadingLinearLayout.setVisibility(View.GONE);
        setRating(mViewModel.getRating());
    }

    private void displayPage(PageType pageType) {
        if (pageType == PageType.RESERVATION) {
            mReserveLinearLayout.setVisibility(View.VISIBLE);
            mShopLinearLayout.setVisibility(View.GONE);
        } else {
            mReserveLinearLayout.setVisibility(View.GONE);
            mShopLinearLayout.setVisibility(View.VISIBLE);
        }
    }

    @SuppressLint("SetTextI18n")
    private void selectDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int dayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(getContext(), (view, y, m, dof) -> {
            Date date = new Date(y, m, dof);
            mDay = String.valueOf(dof);
            mMonth = String.valueOf(m);
            mYear = String.valueOf(y);
            mSelectedDateTextView.setText(StringFormat.date(date));
        }, year, month, dayOfMonth);
        dialog.show();
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    private void selectTime() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR);
        calendar.get(Calendar.AM_PM);

        TimePickerDialog dialog = new TimePickerDialog(getContext(), (view, h, m) -> {
            Time time = new Time(h, m, 0);
            mTime = new SimpleDateFormat("h").format(time);
            mMinute = m + "";
            mMeridian = new SimpleDateFormat("a").format(time);
            mSelectedTimeTextView.setText(StringFormat.time(time));
        }, hour, 0, false);
        dialog.show();
    }

    private void onReadReview() {
        if (getActivity() == null) {
            ToastMessage.display(getContext(), ErrorUtil.getErrorMessage(
                    ErrorUtil.ErrorCode.NO_ACTIVITY_TO_START,
                    ReserveFragment.class
            ));
            return;
        }

        ((MainActivity) getActivity()).replaceFragment(
                ShopReviewFragment.newInstance(mShop.getReview()),
                containerViewId);
    }

    private void setAction(ActionType actionType) {
        if (actionType == ActionType.SUBMIT) {
            mSubmitProgressBar.setVisibility(View.VISIBLE);
            mSubmitButton.setVisibility(View.GONE);
            mSubmitButton.setClickable(false);
            mCancelButton.setClickable(false);
        } else {
            mSubmitProgressBar.setVisibility(View.GONE);
            mSubmitButton.setVisibility(View.VISIBLE);
            mSubmitButton.setClickable(true);
            mCancelButton.setClickable(true);
        }
    }

    @SuppressLint("SetTextI18n")
    private void setRating(double ratingScore) {
        Log.e(ReserveFragment.class.getSimpleName(), "" + ratingScore);
        mRatingReviewTextView.setText(ratingScore + " Read Review");

        mStar1ImageView.setImageResource(R.drawable.ic_star_outline_24);
        mStar2ImageView.setImageResource(R.drawable.ic_star_outline_24);
        mStar3ImageView.setImageResource(R.drawable.ic_star_outline_24);
        mStar4ImageView.setImageResource(R.drawable.ic_star_outline_24);
        mStar5ImageView.setImageResource(R.drawable.ic_star_outline_24);

        if (ratingScore >= 5) {
            mStar5ImageView.setImageResource(R.drawable.ic_star_24);
        }

        if (ratingScore >= 4) {
            mStar4ImageView.setImageResource(R.drawable.ic_star_24);
        }

        if (ratingScore >= 3) {
            mStar3ImageView.setImageResource(R.drawable.ic_star_24);
        }

        if (ratingScore >= 2) {
            mStar2ImageView.setImageResource(R.drawable.ic_star_24);
        }

        if (ratingScore >= 1) {
            mStar1ImageView.setImageResource(R.drawable.ic_star_24);
        }
    }

}