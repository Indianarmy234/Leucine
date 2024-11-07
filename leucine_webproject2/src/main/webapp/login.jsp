<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login</title>
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
        .login-container {
            background-color: #fff;
            padding: 40px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 400px;
            text-align: center;
        }

        /* Title text */
        h2 {
            color: #333;
            margin-bottom: 30px;
            font-size: 28px;
        }

        /* Input styling */
        input[type="text"], input[type="password"] {
            width: 100%;
            padding: 12px;
            margin: 10px 0;
            border: 1px solid #ccc;
            border-radius: 4px;
            font-size: 16px;
            box-sizing: border-box; /* Ensures padding doesn't add extra width */
        }

        /* Focused input field styling */
        input[type="text"]:focus, input[type="password"]:focus {
            border-color: #5b9bd5;
            outline: none;
        }

        /* Submit button styling */
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

        /* Error/success message styling */
        .message {
            margin-top: 20px;
            font-size: 14px;
            color: #d9534f;
        }

        .message.success {
            color: #5bc0de;
        }

        /* Responsive design for small screens */
        @media (max-width: 600px) {
            .login-container {
                padding: 20px;
            }
        }

    </style>
</head>
<body>

    <div class="login-container">
        <h2>Login</h2>
        
        <!-- Display error/success message if any -->
        <c:if test="${param.error != null}">
            <div class="message">${param.error}</div>
        </c:if>
        <c:if test="${param.success != null}">
            <div class="message success">${param.success}</div>
        </c:if>

        <!-- Login form -->
        <form action="LoginServlet" method="post">
            <input type="text" name="username" placeholder="Username" required />
            <input type="password" name="password" placeholder="Password" required />
            <button type="submit">Login</button>
        </form>
    </div>

</body>
</html>
