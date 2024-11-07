<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Request Access</title>
    <style>
        /* General body styling */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
            height: 100%;
            display: flex;
            justify-content: center;
            align-items: center;
        }

        /* Form container */
        .form-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 500px;
            text-align: center;
        }

        /* Title text */
        h2 {
            color: #333;
            margin-bottom: 30px;
            font-size: 28px;
        }

        /* Input and textarea styling */
        select, textarea {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
            box-sizing: border-box; /* Ensures padding doesn't add extra width */
        }

        /* Focused input field styling */
        select:focus, textarea:focus {
            border-color: #5b9bd5;
            outline: none;
        }

        /* Button styling */
        button[type="submit"] {
            width: 100%;
            padding: 14px;
            background-color: #4CAF50;
            color: white;
            font-size: 16px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin-top: 15px;
            transition: background-color 0.3s;
        }

        /* Hover effect for the button */
        button[type="submit"]:hover {
            background-color: #45a049;
        }

        /* Responsive design for small screens */
        @media (max-width: 600px) {
            .form-container {
                padding: 20px;
            }
        }

    </style>
</head>
<body>

    <div class="form-container">
        <h2>Request Software Access</h2>

        <!-- Request Access Form -->
        <form action="RequestServlet" method="post">
            <select name="softwareId" required>
                <!-- Dynamically populated with software names from the database -->
                <option value="">Select Software</option>
                <!-- Example options (These should be populated dynamically using JSP tags and a database query) -->
                <option value="1">Software A</option>
                <option value="2">Software B</option>
                <option value="3">Software C</option>
            </select>

            <select name="accessType" required>
                <option value="">Select Access Type</option>
                <option value="Read">Read</option>
                <option value="Write">Write</option>
                <option value="Admin">Admin</option>
            </select>

            <textarea name="reason" placeholder="Reason for request" required></textarea>

            <button type="submit">Request Access</button>
        </form>
    </div>

</body>
</html>
