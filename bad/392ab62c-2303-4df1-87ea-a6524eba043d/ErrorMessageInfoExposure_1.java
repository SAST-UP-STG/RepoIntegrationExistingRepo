@RestController
public class SqlInjectionLesson6b extends AssignmentEndpoint {

  private final LessonDataSource dataSource;

  public SqlInjectionLesson6b(LessonDataSource dataSource) {
    this.dataSource = dataSource;
  }

  @PostMapping("/SqlInjectionAdvanced/attack6b")
  @ResponseBody
  public AttackResult completed(@RequestParam String userid_6b) throws IOException {
    if (userid_6b.equals(getPassword())) {
      return success(this).build();
    } else {
      return failed(this).build();
    }
  }

  protected String getPassword() {
    String password = "dave";
    try (Connection connection = dataSource.getConnection()) {
      String query = "SELECT password FROM user_system_data WHERE user_name = 'dave'";
      try {
        Statement statement =
            connection.createStatement(
                ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
        ResultSet results = statement.executeQuery(query);

        if (results != null && results.first()) {
          password = results.getString("password");
        }
      } catch (SQLException sqle) {
        sqle.printStackTrace();
        // do nothing
      }
    } catch (Exception e) {
      e.printStackTrace();
      // do nothing
    }
    return (password);
  }

  private String tmp = "1";
}