// package inviteme.restfull.controller;

// import java.sql.Connection;
// import java.sql.DriverManager;

// public class DatabaseConnection {
//     public static void main(String[] args) {
//         String url = "jdbc:mysql://gateway01.ap-southeast-1.prod.aws.tidbcloud.com:4000/test";
//         String username = "34SwiD6HLwi94oz.root";
//         String password = "iMZ8MqJM5x2bCiBt";

//         try (Connection connection = DriverManager.getConnection(url, username, password)) {
//             if (connection != null) {
//                 System.out.println("Successfully connected to the database.");
//             } else {
//                 System.out.println("Failed to connect to the database.");
//             }
//         } catch (Exception e) {
//             e.printStackTrace();
//         }
//     }
// }
