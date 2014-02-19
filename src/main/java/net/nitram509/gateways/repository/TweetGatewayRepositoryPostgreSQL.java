package net.nitram509.gateways.repository;

import net.nitram509.gateways.api.Gateway;
import net.nitram509.gateways.api.GatewayId;
import net.nitram509.gateways.api.UserId;
import net.nitram509.gateways.api.UserProfile;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class TweetGatewayRepositoryPostgreSQL implements TweetGatewayRepository {

  private final Connection connection;

  TweetGatewayRepositoryPostgreSQL(Connection connection) {
    this.connection = connection;
  }

  @Override
  public void save(UserProfile userProfile) {
    delete(userProfile);
    String query = "INSERT INTO userprofile " +
        "        (id, name, screenname, profileimageurl, profileimageurlhttps, url) " +
        " VALUES (? , ?   , ?         , ?              , ?                   , ?)";
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(query);
      int idx = 1;
      statement.setLong(idx++, userProfile.getId().getId());
      statement.setString(idx++, userProfile.getName());
      statement.setString(idx++, userProfile.getScreenName());
      statement.setString(idx++, userProfile.getProfileImageUrl());
      statement.setString(idx++, userProfile.getProfileImageUrlHttps());
      statement.setString(idx++, userProfile.getUrl());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException e) {
          /* ignore */
        }
      }
    }
  }

  public void delete(UserProfile userProfile) {
    String query = "DELETE FROM userprofile " +
        " WHERE id=?";
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(query);
      int idx = 1;
      statement.setLong(1, userProfile.getId().getId());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException e) {
          /* ignore */
        }
      }
    }
  }

  @Override
  public UserProfile getUser(UserId userId) {
    String query = "SELECT" +
        " id, name, screenname, profileimageurl, profileimageurlhttps, url" +
        " FROM userprofile " +
        " WHERE id=?";
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(query);
      statement.setLong(1, userId.getId());
      final ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        final UserProfile profile = new UserProfile(new UserId(resultSet.getLong("id")));
        profile.setName(resultSet.getString("name"));
        profile.setScreenName(resultSet.getString("screenName"));
        profile.setProfileImageUrl(resultSet.getString("profileimageurl"));
        profile.setProfileImageUrlHttps(resultSet.getString("profileimageurlhttps"));
        profile.setUrl(resultSet.getString("url"));
        return profile;
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException e) {
          /* ignore */
        }
      }
    }
    throw new NoSuchElementException("UserProfile doesn't exists for " + userId);
  }

  @Override
  public void save(Gateway gateway) {
    String query = "INSERT INTO gateway " +
        "        (id, owner, activity, suffix) " +
        " VALUES (? , ?    , ?       , ?     )";
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(query);
      int idx = 1;
      statement.setString(idx++, gateway.getId().getId());
      statement.setLong(idx++, gateway.getOwner().getId());
      statement.setInt(idx++, gateway.getActivity());
      statement.setString(idx++, gateway.getSuffix());
      statement.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException e) {
          /* ignore */
        }
      }
    }
  }

  @Override
  public List<Gateway> findGateways(UserId owner) {
    final ArrayList<Gateway> result = new ArrayList<>();
    String query = "SELECT" +
        " id, owner, activity, suffix" +
        " FROM gateway " +
        " WHERE owner=?";
    PreparedStatement statement = null;
    try {
      statement = connection.prepareStatement(query);
      statement.setLong(1, owner.getId());
      final ResultSet resultSet = statement.executeQuery();
      while (resultSet.next()) {
        final Gateway gwi = new Gateway(new GatewayId(resultSet.getString("id")));
        gwi.setOwner(new UserId(resultSet.getLong("owner")));
        gwi.setActivity(resultSet.getInt("activity"));
        gwi.setSuffix(resultSet.getString("suffix"));
        result.add(gwi);
      }
    } catch (SQLException e) {
      throw new RuntimeException(e);
    } finally {
      if (statement != null) {
        try {
          statement.close();
        } catch (SQLException e) {
          /* ignore */
        }
      }
    }
    return result;
  }
}