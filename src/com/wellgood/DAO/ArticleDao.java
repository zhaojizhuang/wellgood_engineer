package com.wellgood.DAO;

import java.sql.SQLException;
import java.util.List;

import android.content.Context;

import com.j256.ormlite.dao.Dao;
import com.wellgood.model.User;

public class ArticleDao
{
	private Dao<Article, Integer> articleDaoOpe;
	private DatabaseHelper helper;

	@SuppressWarnings("unchecked")
	public ArticleDao(Context context)
	{
		try
		{
			helper = DatabaseHelper.getHelper(context);
			articleDaoOpe = helper.getDao(Article.class);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ���һ��Article
	 * @param article
	 */
	public void add(Article article)
	{
		try
		{
			articleDaoOpe.create(article);
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	/**
	 * ͨ��Id�õ�һ��Article
	 * @param id
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Article getArticleWithUser(int id)
	{
		Article article = null;
		try
		{
			article = articleDaoOpe.queryForId(id);
			helper.getDao(User.class).refresh(article.getUser());

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return article;
	}
	
	/**
	 * ͨ��Id�õ�һƪ����
	 * @param id
	 * @return
	 */
	public Article get(int id)
	{
		Article article = null;
		try
		{
			article = articleDaoOpe.queryForId(id);

		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return article;
	}

	/**
	 * ͨ��UserId��ȡ���е�����
	 * @param userId
	 * @return
	 */
	public List<Article> listByUserId(int userId)
	{
		try
		{
			return articleDaoOpe.queryBuilder().where().eq("user_id", userId)
					.query();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
		return null;
	}

}
