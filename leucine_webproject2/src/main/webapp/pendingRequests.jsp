<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Pending Access Requests</title>
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
            align-items: flex-start;
            padding-top: 30px;
        }

        /* Table container */
        .table-container {
            width: 90%;
            max-width: 1000px;
            background-color: #fff;
            padding: 20px;
            border-radius: 8px;
            box-shadow: 0 4px 8px rgba(0, 0, 0, 0.1);
        }

        /* Table styling */
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }

        table th, table td {
            padding: 12px;
            text-align: left;
            border-bottom: 1px solid #ddd;
        }

        /* Table headers styling */
        table th {
            background-color: #4CAF50;
            color: white;
            font-size: 16px;
        }

        /* Alternate row colors */
        table tr:nth-child(even) {
            background-color: #f9f9f9;
        }

        /* Button styling */
        button[type="submit"] {
            padding: 10px 15px;
            font-size: 14px;
            border: none;
            border-radius: 4px;
            cursor: pointer;
            margin: 5px;
            transition: background-color 0.3s;
        }

        /* Approve button */
        button[type="submit"][value="Approved"] {
            background-color: #4CAF50;
            color: white;
        }

        /* Reject button */
        button[type="submit"][value="Rejected"] {
            background-color: #f44336;
            color: white;
        }

        /* Button hover effects */
        button[type="submit"]:hover {
            opacity: 0.8;
        }

        /* Responsive design for smaller screens */
        @media (max-width: 768px) {
            .table-container {
                width: 100%;
                padding: 15px;
            }

            table th, table td {
                padding: 8px;
            }

            button[type="submit"] {
                font-size: 12px;
                padding: 8px 12px;
            }
        }
    </style>
</head>
<body>

    <div class="table-container">
        <h2>Pending Access Requests</h2>

        <!-- Pending Requests Table -->
        <table>
            <tr>
                <th>Employee Name</th>
                <th>Software Name</th>
                <th>Access Type</th>
                <th>Reason</th>
                <th>Actions</th>
            </tr>

            <!-- Dynamically populate with pending requests from the database -->
            <tr>
                <td>John Doe</td>
                <td>Software A</td>
                <td>Write</td>
                <td>To access financial reports</td>
                <td>
                    <form action="ApprovalServlet" method="post">
                        <input type="hidden" name="requestId" value="1" />
                        <button type="submit" name="status" value="Approved">Approve</button>
                        <button type="submit" name="status" value="Rejected">Reject</button>
                    </form>
                </td>
            </tr>
            <!-- Repeat this <tr> block dynamically for each pending request -->
            <tr>
                <td>Jane Smith</td>
                <td>Software B</td>
                <td>Admin</td>
                <td>To manage user permissions</td>
                <td>
                    <form action="ApprovalServlet" method="post">
                        <input type="hidden" name="requestId" value="2" />
                        <button type="submit" name="status" value="Approved">Approve</button>
                        <button type="submit" name="status" value="Rejected">Reject</button>
                    </form>
                </td>
            </tr>

        </table>
    </div>

</body>
</html>
