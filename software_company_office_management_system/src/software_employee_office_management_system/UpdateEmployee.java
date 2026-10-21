void updateEmployee()
{
    String id = JOptionPane.showInputDialog(
        this, "Enter Employee ID to update:");

    if (id == null || id.trim().isEmpty())
        return;

    String sql = "SELECT * FROM employee WHERE id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql))
    {
        ps.setString(1, id.trim());

        try (ResultSet rs = ps.executeQuery())
        {
            if (!rs.next())
            {
                JOptionPane.showMessageDialog(
                    this, "Employee not found!");
                return;
            }

            String name = JOptionPane.showInputDialog(
                this, "Enter Name:", rs.getString("name"));
            if (name == null) return;

            String department = JOptionPane.showInputDialog(
                this, "Enter Department:",
                rs.getString("department"));
            if (department == null) return;

            String designation = JOptionPane.showInputDialog(
                this, "Enter Designation:",
                rs.getString("designation"));
            if (designation == null) return;

            String salary = JOptionPane.showInputDialog(
                this, "Enter Salary:", rs.getString("salary"));
            if (salary == null) return;

            double sal;
            try
            {
                sal = Double.parseDouble(salary.trim());
            }
            catch (NumberFormatException ex)
            {
                JOptionPane.showMessageDialog(
                    this, "Enter a valid salary!");
                return;
            }

            String update = "UPDATE employee SET name=?, "
                + "department=?, designation=?, salary=? WHERE id=?";

            try (PreparedStatement ps2 =
                     con.prepareStatement(update))
            {
                ps2.setString(1, name);
                ps2.setString(2, department);
                ps2.setString(3, designation);
                ps2.setDouble(4, sal);
                ps2.setString(5, id.trim());

                ps2.executeUpdate();

                JOptionPane.showMessageDialog(
                    this, "Employee updated successfully!");
            }
        }
    }
    catch (SQLException ex)
    {
        JOptionPane.showMessageDialog(
            this, ex.getMessage());
    }
}
    void deleteEmployee()
{
    String id = JOptionPane.showInputDialog(
        this, "Enter Employee ID to delete:");

    if (id == null || id.trim().isEmpty())
        return;

    int choice = JOptionPane.showConfirmDialog(
        this, "Delete employee ID " + id + "?",
        "Confirm Delete", JOptionPane.YES_NO_OPTION);

    if (choice != JOptionPane.YES_OPTION)
        return;

    String sql = "DELETE FROM employee WHERE id = ?";

    try (Connection con = DBConnection.getConnection();
         PreparedStatement ps = con.prepareStatement(sql))
    {
        ps.setString(1, id.trim());

        int rows = ps.executeUpdate();

        if (rows > 0)
        {
            JOptionPane.showMessageDialog(
                this, "Employee deleted successfully!");
        }
        else
        {
            JOptionPane.showMessageDialog(
                this, "Employee not found!");
        }
    }
    catch (SQLException ex)
    {
        JOptionPane.showMessageDialog(
            this, ex.getMessage());
    }
}