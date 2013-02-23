package kr.yudonguk.kangwonuniv.foodmenu.data;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;

public class FoodMenu
{
	public static class Food
	{
		public String name;
		public float rate;

		public Food()
		{
			this("");
		}

		public Food(String name)
		{
			this(name, -1.0f);
		}

		public Food(String name_, float rate_)
		{
			name = name_;
			rate = rate_;
		}

		public boolean isGroup()
		{
			return false;
		}
	}

	public static class FoodGroup extends Food
	{
		public final ArrayList<Food> foods = new ArrayList<Food>();

		public FoodGroup()
		{
			this("");
		}

		public FoodGroup(String name)
		{
			super(name);
		}

		@Override
		public boolean isGroup()
		{
			return true;
		}

		public void add(Food food)
		{
			foods.add(food);
		}

		public Food get(int index)
		{
			return foods.get(index);
		}

		public int size()
		{
			return foods.size();
		}
	}

	public static class Section
	{
		public String name;
		public final Calendar startTime = Calendar.getInstance();
		public final Calendar endTime = Calendar.getInstance();
		public final ArrayList<Food> foods = new ArrayList<FoodMenu.Food>();

		private static final TimeZone DEFAULT_TIME_ZONE = TimeZone
				.getTimeZone("Asia/Seoul");

		public Section()
		{
			this("");
		}

		public Section(String name_)
		{
			name = name_;
			setStartTime(0, 0);
			setEndTime(0, 0);
		}

		public void add(Food food)
		{
			foods.add(food);
		}

		public Food get(int index)
		{
			return foods.get(index);
		}

		public int size()
		{
			return foods.size();
		}

		public void setStartTime(int hour, int minute)
		{
			startTime.setTimeZone(DEFAULT_TIME_ZONE);
			startTime.set(Calendar.HOUR_OF_DAY, hour);
			startTime.set(Calendar.MINUTE, minute);
		}

		public void setEndTime(int hour, int minute)
		{
			startTime.setTimeZone(DEFAULT_TIME_ZONE);
			endTime.set(Calendar.HOUR_OF_DAY, hour);
			endTime.set(Calendar.MINUTE, minute);
		}
	}

	public ArrayList<Section> sections = new ArrayList<FoodMenu.Section>();

	public void add(Section section)
	{
		sections.add(section);
	}

	public Section get(int index)
	{
		return sections.get(index);
	}

	public int size()
	{
		return sections.size();
	}
}
