package kr.yudonguk.kangwonuniv.foodmenu.activity;

import kr.yudonguk.kangwonuniv.foodmenu.AsyncDataReader;
import kr.yudonguk.kangwonuniv.foodmenu.R;
import kr.yudonguk.kangwonuniv.foodmenu.data.BaekRokFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.CheonJiFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.DummyFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenu;
import kr.yudonguk.kangwonuniv.foodmenu.data.FoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.data.TaeBaekFoodMenuModel;
import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuPresenter;
import kr.yudonguk.kangwonuniv.foodmenu.ui.FoodMenuView;
import kr.yudonguk.ui.DataReceiver;
import kr.yudonguk.ui.UpdateResult;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class FoodMenuFragment extends Fragment implements FoodMenuPresenter
{
	public static final String ARG_RESTAURANT_NAME = "restaurant_name";

	FoodMenuView mUiView;
	FoodMenuModel mUiModel;
	AsyncDataReader<FoodMenu> asyncDataReader;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState)
	{
		mUiView = new FoodMenuView(this);

		Bundle bundle = getArguments();
		if (bundle != null)
		{
			String restaurantName = bundle.getString(ARG_RESTAURANT_NAME);

			final String[] restaurantList = getResources().getStringArray(
					R.array.restaurant_list);
			int index = 0;
			for (String restaurant : restaurantList)
			{
				if (restaurant.equals(restaurantName))
					break;
				index++;
			}

			switch (index)
			{
				case 0:
					mUiModel = new BaekRokFoodMenuModel(this);
					break;
				case 1:
					mUiModel = new CheonJiFoodMenuModel(this);
					break;
				case 2:
					mUiModel = new TaeBaekFoodMenuModel(this);
				case 3:
				case 4:
				case 5:
			}
		}

		if (mUiModel == null)
		{
			mUiModel = new DummyFoodMenuModel(this);
		}

		asyncDataReader = new AsyncDataReader<FoodMenu>(mUiModel);

		setHasOptionsMenu(true);

		View view = mUiView.onEnabled(inflater);
		if (savedInstanceState != null)
			mUiView.restoreState(savedInstanceState);

		return view;
	}

	@Override
	public void onViewStateRestored(Bundle savedInstanceState)
	{
		super.onViewStateRestored(savedInstanceState);
		if (savedInstanceState != null)
			mUiView.restoreState(savedInstanceState);
	}

	@Override
	public void onSaveInstanceState(Bundle outState)
	{
		super.onSaveInstanceState(outState);
		mUiView.saveState(outState);
	}

	@Override
	public void onPause()
	{
		super.onPause();
		mUiView.onDisabled();
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater)
	{
		mUiView.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		return mUiView.onOptionsItemSelected(item) ? true : super
				.onOptionsItemSelected(item);
	}

	@Override
	public void onModelUpdated(final UpdateResult result)
	{
		getActivity().runOnUiThread(new Runnable()
		{
			@Override
			public void run()
			{
				if (result.isSuccess)
					mUiView.update();
				else
					mUiView.onError(result);
			}
		});
	}

	@Override
	public void getData(final int id, final DataReceiver<FoodMenu> receiver)
	{
		asyncDataReader.execute(id, receiver);
	}

	@Override
	public void cancelGetting(int id)
	{
		asyncDataReader.cancel(id);
	}

	@Override
	public void setData(int id, FoodMenu data)
	{
		mUiModel.setData(id, data);
	}
}