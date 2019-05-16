package objectoriented


sealed trait PreTaxDeductions
sealed trait PostTaxDeductions
sealed trait Final

case class Employee (
                    name: String,
                    annualSalary: Float,
                    taxRate: Float,
                    insurancePremiumsPerPayPeriod: Float,
                    _401kDeductionRate: Float,
                    postTaxDeductions: Float
                    )

case class Pay[Step](employee:  Employee, netPay: Float)
object Payroll {
  // biweekly paychecks. Assume exactly 52 weeks
  def start(employee: Employee): Pay[PreTaxDeductions] = Pay[PreTaxDeductions](employee, employee.annualSalary / 26.0F)

  def minusInsurance(pay: Pay[PreTaxDeductions]): Pay[PreTaxDeductions] = {
    val newNet = pay.netPay - pay.employee.insurancePremiumsPerPayPeriod
    pay copy (netPay = newNet)
  }

  def minus401k(pay: Pay[PreTaxDeductions]): Pay[PreTaxDeductions] = {
    val newNet = pay.netPay - (pay.employee._401kDeductionRate * pay.netPay)
    pay copy (netPay = newNet)
  }

  def minusTax(pay: Pay[PreTaxDeductions]): Pay[PostTaxDeductions] = {
    val newNet = pay.netPay - (pay.employee.taxRate * pay.netPay)
    pay copy (netPay = newNet)
  }

  def minusFinalDeductions(pay: Pay[PostTaxDeductions]): Pay[Final] = {
    val newNet = pay.netPay - pay.employee.postTaxDeductions
    pay copy (netPay = newNet)
  }
}


object CalculatePayroll {
  def main(args: Array[String]) = {
    val e = Employee("Buck Trends", 10000.0F, 0.25F, 200F, 0.10F, 0.05F)
    val pay1 = Payroll start e
    val pay2 = Payroll minus401k pay1
    val pay3 = Payroll minusInsurance pay2
    val pay4 = Payroll minusTax pay3
    val pay = Payroll minusFinalDeductions pay4
    val twoWeekGross = e.annualSalary / 26.0F
    val twoWeekNet = pay.netPay
    val percent = (twoWeekNet / twoWeekGross) * 100
    println(s"For ${e.name}, the gross vs. net pay every 2 week is: ")
    println(f"$$${twoWeekGross}%.2f vs $$${twoWeekNet}%.2f or ${percent}%.1f%%")
  }
}
