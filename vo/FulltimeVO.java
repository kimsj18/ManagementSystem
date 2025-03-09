package vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FulltimeVO extends EmployeeVO implements Comparable<FulltimeVO>{

    private int result; // 실적
    private int basicSalary; // 월급


    public FulltimeVO(String empNo, String name, int result, int basicSalary){
      super(name, empNo);
      this.result = result;
      this.basicSalary = basicSalary;
    }

    @Override
    public boolean equals(Object o) {
        // FulltimeVO 타입 체크, 객체 자기 자신과 비교
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
        FulltimeVO that = (FulltimeVO) o;
        return Objects.equals(getEmpNo(), that.getEmpNo());
    }

    @Override
    public int compareTo(FulltimeVO o) {
        return this.getEmpNo().compareTo(o.getEmpNo());
    }

    @Override
    public String toString() {
        String str = "\t%-13s%-14s%-13s%-20s";
        return String.format(str, getEmpNo(), getName(), result, basicSalary);
    }

}




