package com.wellgood.pulltorefresh;


import android.content.Context;
import android.text.format.Time;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.wellgood.engineer.R;

public class PullToRefreshView extends LinearLayout {
	private static final String TAG = "PullToRefreshView";
	// refresh states
	private static final int PULL_TO_REFRESH = 2;
	private static final int RELEASE_TO_REFRESH = 3;
	private static final int REFRESHING = 4;
	// pull state
	private static final int PULL_UP_STATE = 0;
	private static final int PULL_DOWN_STATE = 1;
	/**
	 * last y
	 */
	private int mLastMotionY;
	/**
	 * lock
	 */
	private boolean mLock;
	/**
	 * header view
	 */
	private View mHeaderView;
	/**
	 * footer view
	 */
	private View mFooterView;
	/**
	 * list or grid
	 */
	private AdapterView<?> mAdapterView;
	/**
	 * scrollview
	 */
	private ScrollView mScrollView;
	/**
	 * header view height
	 */
	private int mHeaderViewHeight;
	/**
	 * footer view height
	 */
	private int mFooterViewHeight;
	/**
	 * header view image
	 */
	private ImageView mHeaderImageView;
	/**
	 * footer view image
	 */
	private ImageView mFooterImageView;
	/**
	 * header tip text
	 */
	private TextView mHeaderTextView;
	/**
	 * footer tip text
	 */
	private TextView mFooterTextView;
	/**
	 * header refresh time
	 */
	private TextView mHeaderUpdateTextView;
	/**
	 * footer refresh time
	 */
	// private TextView mFooterUpdateTextView;
	/**
	 * header progress bar
	 */
	private ProgressBar mHeaderProgressBar;
	/**
	 * footer progress bar
	 */
	private ProgressBar mFooterProgressBar;
	/**
	 * layout inflater
	 */
	private LayoutInflater mInflater;
	/**
	 * header view current state
	 */
	private int mHeaderState;
	/**
	 * footer view current state
	 */
	private int mFooterState;
	/**
	 * pull state,pull up or pull down;PULL_UP_STATE or PULL_DOWN_STATE
	 */
	private int mPullState;
	/**
	 * 脗猫貌鈥扳垙鈭偯�?��扳垙茫脕枚脩脕脝鈮犆偮�,脢卯蟺脗猫貌脕脝鈮犆偮ッ娒毕�脗锚�?
	 */
	private RotateAnimation mFlipAnimation;
	/**
	 * 脗猫貌鈥扳垙鈭埫劽溍偯伱睹懨伱嗏墵脗搂�?,脢贸茫脣惟篓
	 */
	private RotateAnimation mReverseFlipAnimation;
	/**
	 * footer refresh listener
	 */
	private OnFooterRefreshListener mOnFooterRefreshListener;
	/**
	 * footer refresh listener
	 */
	private OnHeaderRefreshListener mOnHeaderRefreshListener;
	/**
	 * last update time
	 */
	@SuppressWarnings("unused")
	private String mLastUpdateTime;

	public PullToRefreshView(Context context, AttributeSet attrs) {
		super(context, attrs);
		init();
	}

	public PullToRefreshView(Context context) {
		super(context);
		init();
	}

	/**
	 * init
	 * 
	 * @description
	 * @param context
	 *            hylin 2012-7-26鈥扳垙盲脗莽�?10:08:33
	 */
	private void init() {
		// Load all of the animations we need in code rather than through XML
		mFlipAnimation = new RotateAnimation(0, -180,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mFlipAnimation.setInterpolator(new LinearInterpolator());
		mFlipAnimation.setDuration(250);
		mFlipAnimation.setFillAfter(true);
		mReverseFlipAnimation = new RotateAnimation(-180, 0,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f,
				RotateAnimation.RELATIVE_TO_SELF, 0.5f);
		mReverseFlipAnimation.setInterpolator(new LinearInterpolator());
		mReverseFlipAnimation.setDuration(250);
		mReverseFlipAnimation.setFillAfter(true);

		mInflater = LayoutInflater.from(getContext());
		// header view
		// 脗煤庐脢鈮犅娾垜陋脗盲鈥�,鈥懊该姑嬅樏吤娒裁樏伮ㄢ�扳垙脛鈥扳垙鈩⒚娾垜陋脗盲鈥犆偯犫垶linearlayout脕枚脩脢煤脛鈥扳垙盲脕麓�?
		addHeaderView();
	}

	private void addHeaderView() {
		// header view
		mHeaderView = mInflater.inflate(R.layout.refresh_header, this, false);

		mHeaderImageView = (ImageView) mHeaderView
				.findViewById(R.id.pull_to_refresh_image);
		mHeaderTextView = (TextView) mHeaderView
				.findViewById(R.id.pull_to_refresh_text);
		mHeaderUpdateTextView = (TextView) mHeaderView
				.findViewById(R.id.pull_to_refresh_updated_at);
		mHeaderProgressBar = (ProgressBar) mHeaderView
				.findViewById(R.id.pull_to_refresh_progress);
		// header layout
		measureView(mHeaderView);
		mHeaderViewHeight = mHeaderView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mHeaderViewHeight);
		// 脣脝忙脕惟脝topMargin脕枚脩脗脛潞鈥扳垙鈭�?嬄ッ济伱睹慼eader
		// View脠麓貌脗鈭�?,脗莽鈮ッ傗垶脺脗脰鈭偯埫睹�?�嬅趁偯郝娒好勨�扳垙盲脢帽�?
		params.topMargin = -(mHeaderViewHeight);
		// mHeaderView.setLayoutParams(params1);
		addView(mHeaderView, params);

	}

	private void addFooterView() {
		// footer view
		mFooterView = mInflater.inflate(R.layout.refresh_footer, this, false);
		mFooterImageView = (ImageView) mFooterView
				.findViewById(R.id.pull_to_load_image);
		mFooterTextView = (TextView) mFooterView
				.findViewById(R.id.pull_to_load_text);
		mFooterProgressBar = (ProgressBar) mFooterView
				.findViewById(R.id.pull_to_load_progress);
		// footer layout
		measureView(mFooterView);
		mFooterViewHeight = mFooterView.getMeasuredHeight();
		LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT,
				mFooterViewHeight);
		// int top = getHeight();
		// params.topMargin
		// =getHeight();//脗煤庐脣酶么脠谩氓getHeight()==0,鈥拔┟溍偯郝畂nInterceptTouchEvent()脢帽蟺脢鈮ッ埫∶etHeight()脗鈭戔墹脕陋猫脢煤芒脗脛潞鈥扳埆�?,鈥扳垙莽脗脺莽脢貌脴0;
		// getHeight()鈥奥勨�跋�脿脢贸鈭偯偯劽粹�奥好睹嬄得Ｃ偯劼�?,脕庐莽脗脛么脗脺莽脕鈥犆伮┾垈鈥扳垙脛鈥扳垙茫
		// 脕卯卤鈥扳埆茅脢貌脴脕鈭�?该娒劽熋傗垙脡脗卤脛脗猫脴鈥奥�⒚伱德ッ娒┾�⒚娾垜陋脗盲鈥�,脗猫鈩⒚嬄睹匒dapterView脕枚脩脠麓貌脗鈭睹娒裁楳ATCH_PARENT,脠脟拢鈥跋�脿footer
		// view脗鈭灺扁�奥好睹嬄⒙疵娾垜陋脗盲鈥犆偯犫垶脢煤脛脗锚�?,脗蟺鈭偯埫睹嬅趁�
		addView(mFooterView, params);
	}

	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();
		// footer view 脗煤庐脢鈮犅娾垜陋脗盲鈥犫�懊该姑嬅樏吤娾垜陋脗盲鈥犆偯犫垶linearlayout鈥扳垙鈮犆伱睹懨娒好劽偯�
		addFooterView();
		initContentAdapterView();
	}

	/**
	 * init AdapterView like ListView,GridView and so on;or init ScrollView
	 * 
	 * @description hylin 2012-7-30鈥扳垙茫脗莽�?8:48:12
	 */
	private void initContentAdapterView() {
		int count = getChildCount();
		if (count < 3) {
			throw new IllegalArgumentException(
					"this layout must contain 3 child views,and AdapterView or ScrollView must in the second position!");
		}
		View view = null;
		for (int i = 0; i < count - 1; ++i) {
			view = getChildAt(i);
			if (view instanceof AdapterView<?>) {
				mAdapterView = (AdapterView<?>) view;
			}
			if (view instanceof ScrollView) {
				// finish later
				mScrollView = (ScrollView) view;
			}
		}
		if (mAdapterView == null && mScrollView == null) {
			throw new IllegalArgumentException(
					"must contain a AdapterView or ScrollView in this layout!");
		}
	}

	private void measureView(View child) {
		ViewGroup.LayoutParams p = child.getLayoutParams();
		if (p == null) {
			p = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT);
		}

		int childWidthSpec = ViewGroup.getChildMeasureSpec(0, 0 + 0, p.width);
		int lpHeight = p.height;
		int childHeightSpec;
		if (lpHeight > 0) {
			childHeightSpec = MeasureSpec.makeMeasureSpec(lpHeight,
					MeasureSpec.EXACTLY);
		} else {
			childHeightSpec = MeasureSpec.makeMeasureSpec(0,
					MeasureSpec.UNSPECIFIED);
		}
		child.measure(childWidthSpec, childHeightSpec);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent e) {
		int y = (int) e.getRawY();
		switch (e.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 脠露帽脗脰脿脢茫露脢脿鈩own鈥扳埆茫鈥奥垈,脣脝鈭灻偽┟痽脗霉锚脢鈥犆�
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			// deltaY > 0 脢貌脴脗锚毛鈥扳垙茫脣酶锚脗盲庐,< 0脢貌脴脗锚毛鈥扳垙盲脣酶锚脗盲庐
			int deltaY = y - mLastMotionY;
			if (isRefreshViewScroll(deltaY)) {
				return true;
			}
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			break;
		}
		return false;
	}

	/*
	 * 脗露脟脢没煤脗煤庐onInterceptTouchEvent()脢帽蟺脢鈮ッ�扳垙鈮犆娾墹掳脢煤芒脢茫露脢脿鈩�?(脗莽鈮�
	 * onInterceptTouchEvent()脢帽蟺脢鈮ッ�扳垙鈮� return false)脗脿么脕卯卤PullToRefreshView
	 * 脕枚脩脗
	 * 鈮犆猇iew脢霉鈥⒚偮懨伱�;脗锚露脗脿么脕卯卤鈥扳垙茫脠霉垄脕枚脩脢帽蟺脢鈮ッ娒光�⒚偮懨伱�(脗莽鈮ッ伱盤ullToRefreshView脣谩
	 * 鈩⒚傗垜卤脢霉鈥⒚偮懨伱�?��)
	 */
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (mLock) {
			return true;
		}
		int y = (int) event.getRawY();
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// onInterceptTouchEvent脗鈭戔墹脕陋猫脣脝鈭灻偽┟�
			// mLastMotionY = y;
			break;
		case MotionEvent.ACTION_MOVE:
			int deltaY = y - mLastMotionY;
			if (mPullState == PULL_DOWN_STATE) {
				// PullToRefreshView脢芒脽脣掳氓鈥扳垙茫脢茫�?
				Log.i(TAG, " pull down!parent view move!");
				headerPrepareToRefresh(deltaY);
				// setHeaderPadding(-mHeaderViewHeight);
			} else if (mPullState == PULL_UP_STATE) {
				// PullToRefreshView脢芒脽脣掳氓鈥扳垙盲脢茫�?
				Log.i(TAG, "pull up!parent view move!");
				footerPrepareToRefresh(deltaY);
			}
			mLastMotionY = y;
			break;
		case MotionEvent.ACTION_UP:
		case MotionEvent.ACTION_CANCEL:
			int topMargin = getHeaderTopMargin();
			if (mPullState == PULL_DOWN_STATE) {
				if (topMargin >= 0) {
					// 脗潞脛脗脽茫脗脿鈭懨娒扁�?
					headerRefreshing();
				} else {
					// 脣酶貌脢鈮ぢ懊娒好⒚娒⒚熋嬄懊ッ偯犫垜脢帽鈭灻斅好ッ埫∶娒扁垶脠枚锚脣贸猫
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			} else if (mPullState == PULL_UP_STATE) {
				if (Math.abs(topMargin) >= mHeaderViewHeight
						+ mFooterViewHeight) {
					// 脗潞脛脗脽茫脢芒脽脣掳氓footer 脗脿鈭懨娒扁�?
					footerRefreshing();
				} else {
					// 脣酶貌脢鈮ぢ懊娒好⒚娒⒚熋嬄懊ッ偯犫垜脢帽鈭灻斅好ッ埫∶娒扁垶脠枚锚脣贸猫
					setHeaderTopMargin(-mHeaderViewHeight);
				}
			}
			break;
		}
		return super.onTouchEvent(event);
	}

	/**
	 * 脢貌脴脗锚露脗鈭�?嬅樷�⒚偯犫垶鈥扳埆脺脕脿鈭俈iew,脗莽鈮ullToRefreshView脢陋毛脗盲庐
	 * 
	 * @param deltaY
	 *            , deltaY > 0 脢貌脴脗锚毛鈥扳垙茫脣酶锚脗盲庐,< 0脢貌脴脗锚毛鈥扳垙盲脣酶锚脗盲庐
	 * @return
	 */
	private boolean isRefreshViewScroll(int deltaY) {
		if (mHeaderState == REFRESHING || mFooterState == REFRESHING) {
			return false;
		}
		// 脗脴蟺鈥扳埆茅ListView脗铆氓GridView
		if (mAdapterView != null) {
			// 脗鈮犆獀iew(ListView or GridView)脢陋毛脗盲庐脗脿鈭灻娒好劽埪扳垈脕麓�?
			if (deltaY > 0) {

				View child = mAdapterView.getChildAt(0);
				if (child == null) {
					// 脗露脟脢没煤mAdapterView鈥扳垙鈮犆娾墹掳脢煤芒脢茂鈭灻娒�,鈥扳垙莽脢茫露脢脿鈩�?
					return false;
				}
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& child.getTop() == 0) {
					mPullState = PULL_DOWN_STATE;
					return true;
				}
				int top = child.getTop();
				int padding = mAdapterView.getPaddingTop();
				if (mAdapterView.getFirstVisiblePosition() == 0
						&& Math.abs(top - padding) <= 8) {// 脣酶么脠谩氓鈥跋�茫脗芒莽脕卯庐3脗猫脴鈥奥�⒚偯犅娒扁墵,鈥拔┟溍伱┾垶脗煤庐鈥扳垙莽脣掳�?,脣酶貌脢鈮ぢ懊娒⒚γ偯犫垶脗茅眉脗玫鈥�?
					mPullState = PULL_DOWN_STATE;
					return true;
				}

			} else if (deltaY < 0) {
				View lastChild = mAdapterView.getChildAt(mAdapterView
						.getChildCount() - 1);
				if (lastChild == null) {
					// 脗露脟脢没煤mAdapterView鈥扳垙鈮犆娾墹掳脢煤芒脢茂鈭灻娒�,鈥扳垙莽脢茫露脢脿鈩�?
					return false;
				}
				// 脢煤脛脗锚茅鈥扳垙脛鈥扳垙鈩⒚傗墵锚view脕枚脩Bottom脗鈭灻ㄢ�扳埆茅脕脿鈭俈iew脕枚脩脠麓貌脗鈭睹嬅樎ッ娒裁﹎AdapterView脕枚脩脢茂鈭灻娒喢娾墹掳脢煤芒脗掳麓脢陋掳脕脿鈭�?�iew,
				// 脕鈮犆⑩�扳埆茅脕脿鈭俈iew脕枚脩脠麓貌脗鈭睹嬅樎ッ娒裁﹎AdapterView脗鈭戔墹脕陋猫脢陋毛脗盲庐脗脿鈭灻娒好劽偯�?��
				if (lastChild.getBottom() <= getHeight()
						&& mAdapterView.getLastVisiblePosition() == mAdapterView
								.getCount() - 1) {
					mPullState = PULL_UP_STATE;
					return true;
				}
			}
		}
		// 脗脴蟺鈥扳埆茅ScrollView
		if (mScrollView != null) {
			// 脗鈮犆猻croll view脢陋毛脗盲庐脗脿鈭灻娒好劽埪扳垈脕麓�?
			View child = mScrollView.getChildAt(0);
			if (deltaY > 0 && mScrollView.getScrollY() == 0) {
				mPullState = PULL_DOWN_STATE;
				return true;
			} else if (deltaY < 0
					&& child.getMeasuredHeight() <= getHeight()
							+ mScrollView.getScrollY()) {
				mPullState = PULL_UP_STATE;
				return true;
			}
		}
		return false;
	}

	/**
	 * header 脗谩脺脗搂谩脗脿鈭懨娒扁�?,脢芒茫脢氓谩脕脽陋脗盲庐脣酶谩脕庐茫,脣酶貌脢鈮ぢ懊娒好⒚埫∶っ娒�
	 * 
	 * @param deltaY
	 *            ,脢芒茫脢氓谩脢陋毛脗盲庐脕枚脩脣鈭懨姑伮堵�
	 */
	private void headerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 脗惟矛header
		// view脕枚脩topMargin>=0脢贸鈭偯斅好ッ嬅樎ッ娒裁┟傗垜鈮っ伮偯喢ッ偯柭娒裁γ伮�?埆脗谩鈭�?娒光�⑩�扳埆脺,鈥懊该喢娒�header
		// view 脕枚脩脢猫锚脕搂鈭伱も垈脢脛脜
		if (newTopMargin >= 0 && mHeaderState != RELEASE_TO_REFRESH) {
			mHeaderTextView.setText(R.string.pull_to_refresh_release_label);
			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(mFlipAnimation);
			mHeaderState = RELEASE_TO_REFRESH;
		} else if (newTopMargin < 0 && newTopMargin > -mHeaderViewHeight) {// 脢茫帽脗盲庐脢贸鈭偯娾墹掳脢煤芒脠谩盲脢卯忙
			mHeaderImageView.clearAnimation();
			mHeaderImageView.startAnimation(mFlipAnimation);
			// mHeaderImageView.
			mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
			mHeaderState = PULL_TO_REFRESH;
		}
	}

	/**
	 * footer 脗谩脺脗搂谩脗脿鈭懨娒扁�?,脢芒茫脢氓谩脕脽陋脗盲庐脣酶谩脕庐茫,脣酶貌脢鈮ぢ懊娒好⒚埫∶っ娒� 脕脽陋脗盲庐footer
	 * view脠麓貌脗鈭睹偯ッ娾�犫垜脗铆氓脕脽陋脗盲庐header view
	 * 脠麓貌脗鈭睹娒裁樷�扳垙脛脢鈥犫垜脭潞氓脠脡惟脢貌脴脠脛枚脣酶谩鈥懊该喢娒�header
	 * view脕枚脩topmargin脕枚脩脗脛潞脢霉鈥⒚嬅γγ偯犫垶
	 * 
	 * @param deltaY
	 *            ,脢芒茫脢氓谩脢陋毛脗盲庐脕枚脩脣鈭懨姑伮堵�
	 */
	private void footerPrepareToRefresh(int deltaY) {
		int newTopMargin = changingHeaderViewTopMargin(deltaY);
		// 脗露脟脢没煤header view topMargin 脕枚脩脕陋霉脗脴蟺脗脛潞脗搂脽鈥扳埆茅脢脿帽脕鈮犆⑩�扳埆茅header +
		// footer 脕枚脩脠麓貌脗鈭�
		// 脣脴楼脢貌茅footer view 脗脝氓脗脰庐脢貌忙脕搂鈭�?偯♀埆脢霉鈥⑩�扳埆脺脭潞氓鈥懊该喢娒�footer view
		// 脕枚脩脢猫锚脕搂鈭伱も垈脢脛脜
		if (Math.abs(newTopMargin) >= (mHeaderViewHeight + mFooterViewHeight)
				&& mFooterState != RELEASE_TO_REFRESH) {
			mFooterTextView
					.setText(R.string.pull_to_refresh_footer_release_label);
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(mFlipAnimation);
			mFooterState = RELEASE_TO_REFRESH;
		} else if (Math.abs(newTopMargin) < (mHeaderViewHeight + mFooterViewHeight)) {
			mFooterImageView.clearAnimation();
			mFooterImageView.startAnimation(mFlipAnimation);
			mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
			mFooterState = PULL_TO_REFRESH;
		}
	}

	/**
	 * 鈥懊该喢娒�Header view top margin脕枚脩脗脛潞
	 * 
	 * @description
	 * @param deltaY
	 * @return hylin 2012-7-31鈥扳垙茫脗莽�?1:14:31
	 */
	private int changingHeaderViewTopMargin(int deltaY) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		float newTopMargin = params.topMargin + deltaY * 0.3f;
		// 脣酶么脠谩氓脗脴蟺鈥扳垙盲脢茫芒脗脜枚鈥扳垙脛鈥扳垙茫脠么锚脗脿鈭�,脗玫鈥犫�扳垙鈭�?偽┟偯⒚�?�扳垙盲脢茫芒脗锚茅脕脩鈭偯偯�?�┾�扳垙莽脠谩盲脢卯忙脢芒茫脢氓谩脕玫楼脢茅鈥⑩�扳垙茫脢茫芒,鈥奥好睹娒っも�扳垙茫脢茫芒脗脿鈭懨娒扁垶脕陋么脣脽露脗猫毛鈥扳埆脺,脢脩眉脣鈭灺⒚佄┟偯ufengzungzhe脕枚脩脢氓谩脗谩鈭�
		// 脣掳庐脕搂鈭�?偮睹嚸娒幻好娒裁樏偯郝�扳垙盲脢茫芒脗锚茅鈥扳垙脛脢脝碌脣鈭懨姑伮堵�,脕脩鈭偯偯�?�┟伱德ッ娒┾�⑩�扳垙茫脢茫芒
		if (deltaY > 0 && mPullState == PULL_UP_STATE
				&& Math.abs(params.topMargin) <= mHeaderViewHeight) {
			return params.topMargin;
		}
		// 脗锚氓脢鈥犫垜脗煤鈭�?,脗脴蟺鈥扳垙茫脢茫芒脗脜枚鈥扳垙脛鈥扳垙茫脠么锚脗脿鈭�,脠脜酶脗脰莽脗谩鈭伱┾垶脣鈭懨尖�扳垙盲脢茫芒脢矛莽鈥拔┟好娒斥垈鈥扳垙脛脢鈥犫垜脕枚脩bug
		if (deltaY < 0 && mPullState == PULL_DOWN_STATE
				&& Math.abs(params.topMargin) >= mHeaderViewHeight) {
			return params.topMargin;
		}
		params.topMargin = (int) newTopMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
		return params.topMargin;
	}

	/**
	 * header refreshing
	 * 
	 * @description hylin 2012-7-31鈥扳垙盲脗莽�?9:10:12
	 */
	private void headerRefreshing() {
		mHeaderState = REFRESHING;
		setHeaderTopMargin(0);
		mHeaderImageView.setVisibility(View.GONE);
		mHeaderImageView.clearAnimation();
		mHeaderImageView.setImageDrawable(null);
		mHeaderProgressBar.setVisibility(View.VISIBLE);
		mHeaderTextView.setText(R.string.pull_to_refresh_refreshing_label);
		if (mOnHeaderRefreshListener != null) {
			mOnHeaderRefreshListener.onHeaderRefresh(this);
		}
	}

	/**
	 * footer refreshing
	 * 
	 * @description hylin 2012-7-31鈥扳垙盲脗莽�?9:09:59
	 */
	private void footerRefreshing() {
		mFooterState = REFRESHING;
		int top = mHeaderViewHeight + mFooterViewHeight;
		setHeaderTopMargin(-top);
		mFooterImageView.setVisibility(View.GONE);
		mFooterImageView.clearAnimation();
		mFooterImageView.setImageDrawable(null);
		mFooterProgressBar.setVisibility(View.VISIBLE);
		mFooterTextView
				.setText(R.string.pull_to_refresh_footer_refreshing_label);
		if (mOnFooterRefreshListener != null) {
			mOnFooterRefreshListener.onFooterRefresh(this);
		}
	}

	/**
	 * 脣脝忙脕惟脝header view 脕枚脩topMargin脕枚脩脗脛潞
	 * 
	 * @description
	 * @param topMargin
	 *            脭潞氓鈥扳垙鈭�0脢贸鈭偯斅好ッ嬅樎ッ娒裁﹉eader view 脗脿枚脗鈥⑽┟偯喢ッ偯柭娒裁γ伮р埆脗谩鈭�?娒光�⒚斅好�?
	 *            鈥扳垙鈭�?-mHeaderViewHeight脢贸鈭偯斅好ッ嬅樎ッ娒裁┟偯喢ッ偯柭埫睹�?�嬅趁ㄢ�扳埆脺 hylin
	 *            2012-7-31鈥扳垙盲脗莽�?11:24:06
	 */
	private void setHeaderTopMargin(int topMargin) {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		params.topMargin = topMargin;
		mHeaderView.setLayoutParams(params);
		invalidate();
	}

	/**
	 * header view 脗脝氓脢脿锚脢玫楼脢帽鈭灻偯┟娒吢⒚偮偯犆姑偯熋Ｃ伱も垈脢脛脜
	 * 
	 * @description hylin 2012-7-31鈥扳垙盲脗莽�?11:54:23
	 */
	public void onHeaderRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mHeaderImageView.setVisibility(View.VISIBLE);
		mHeaderImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow);
		mHeaderTextView.setText(R.string.pull_to_refresh_pull_label);
		mHeaderProgressBar.setVisibility(View.GONE);

		Time localTime = new Time("Asia/Hong_Kong");
		localTime.setToNow();
		String date = localTime.format("%m-%d %H:%M");

		mHeaderUpdateTextView.setText("更新时间" + date);
		mHeaderState = PULL_TO_REFRESH;
	}

	/**
	 * Resets the list to a normal state after a refresh.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void onHeaderRefreshComplete(CharSequence lastUpdated) {
		setLastUpdated(lastUpdated);
		onHeaderRefreshComplete();
	}

	/**
	 * footer view 脗脝氓脢脿锚脢玫楼脢帽鈭灻偯┟娒吢⒚偮偯犆姑偯熋Ｃ伱も垈脢脛脜
	 */
	public void onFooterRefreshComplete() {
		setHeaderTopMargin(-mHeaderViewHeight);
		mFooterImageView.setVisibility(View.VISIBLE);
		mFooterImageView.setImageResource(R.drawable.ic_pulltorefresh_arrow_up);
		mFooterTextView.setText(R.string.pull_to_refresh_footer_pull_label);
		mFooterProgressBar.setVisibility(View.GONE);
		// mHeaderUpdateTextView.setText("");
		mFooterState = PULL_TO_REFRESH;
	}

	/**
	 * Set a text to represent when the list was last updated.
	 * 
	 * @param lastUpdated
	 *            Last updated at.
	 */
	public void setLastUpdated(CharSequence lastUpdated) {
		if (lastUpdated != null) {
			mHeaderUpdateTextView.setVisibility(View.VISIBLE);
			mHeaderUpdateTextView.setText(lastUpdated);
		} else {
			mHeaderUpdateTextView.setVisibility(View.GONE);
		}
	}

	/**
	 * 脣茅鈭懨偯泵偽┟偯⒚eader view 脕枚脩topMargin
	 * 
	 * @description
	 * @return hylin 2012-7-31鈥扳垙盲脗莽�?11:22:50
	 */
	private int getHeaderTopMargin() {
		LayoutParams params = (LayoutParams) mHeaderView.getLayoutParams();
		return params.topMargin;
	}

	/**
	 * lock
	 * 
	 * @description hylin 2012-7-27鈥扳垙茫脗莽�?6:52:25
	 */
	@SuppressWarnings("unused")
	private void lock() {
		mLock = true;
	}

	/**
	 * unlock
	 * 
	 * @description hylin 2012-7-27鈥扳垙茫脗莽�?6:53:18
	 */
	@SuppressWarnings("unused")
	private void unlock() {
		mLock = false;
	}

	/**
	 * set headerRefreshListener
	 * 
	 * @description
	 * @param headerRefreshListener
	 *            hylin 2012-7-31鈥扳垙盲脗莽�?11:43:58
	 */
	public void setOnHeaderRefreshListener(
			OnHeaderRefreshListener headerRefreshListener) {
		mOnHeaderRefreshListener = headerRefreshListener;
	}

	public void setOnFooterRefreshListener(
			OnFooterRefreshListener footerRefreshListener) {
		mOnFooterRefreshListener = footerRefreshListener;
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid footer
	 * view should be refreshed.
	 */
	public interface OnFooterRefreshListener {
		public void onFooterRefresh(PullToRefreshView view);
	}

	/**
	 * Interface definition for a callback to be invoked when list/grid header
	 * view should be refreshed.
	 */
	public interface OnHeaderRefreshListener {
		public void onHeaderRefresh(PullToRefreshView view);
	}
}
