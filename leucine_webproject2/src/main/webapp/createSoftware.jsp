<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Create Software</title>
    <style>
        /* General body styling */
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f7fc;
            margin: 0;
            padding: 0;
            display: flex;
            justify-content: center;
            align-items: center;
            height: 100vh;
            color: #333;
        }

        /* Form container styling */
        .form-container {
            background-color: #fff;
            padding: 30px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
            width: 100%;
            max-width: 600px;
        }

        /* Title styling */
        .form-container h2 {
            text-align: center;
            color: #4CAF50;
        }

        /* Form element styling */
        input[type="text"],
        textarea,
        select {
            width: 100%;
            padding: 12px;
            margin: 10px 0 20px;
            border-radius: 4px;
            border: 1px solid #ddd;
            font-size: 16px;
            box-sizing: border-box;
        }

        /* Textarea styling */
        textarea {
            height: 150px;
            resize: vertical;
        }

        /* Select box styling */
        select {
            background-color: #fff;
            color: #333;
        }

        /* Button styling */
        button[type="submit"] {
            width: 100%;
            padding: 12px;
            font-size: 16px;
            background-color: #4CAF50;
            color: white;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            transition: background-color 0.3s ease;
        }

        button[type="submit"]:hover {
            background-color: #45a049;
        }

        /* Responsive Design */
        @media (max-width: 768px) {
            .form-container {
                padding: 20px;
            }

            input[type="text"],
            textarea,
            select,
            button[type="submit"] {
                font-size: 14px;
                padding: 10px;
            }
        }

    </style>
</head>
<body>

    <!-- Form container -->
    <div class="form-container">
        <h2>Create New Software</h2>

        <!-- Software creation form -->
        <form action="SoftwareServlet" method="post">
            <input type="text" name="name" placeholder="Software Name" required />
            <textarea name="description" placeholder="Software Description" required></textarea>
            <select name="accessLevels">
                <option value="Read">Read</option>
                <option value="Write">Write</option>
                <option value="Admin">Admin</option>
            </select>
            <button type="submit">Create Software</button>
        </form>
    </div>

</body>
</html>

