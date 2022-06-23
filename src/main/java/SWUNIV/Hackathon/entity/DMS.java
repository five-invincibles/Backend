package SWUNIV.Hackathon.entity;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Embeddable;
import lombok.NoArgsConstructor;
import lombok.ToString;

/**
 * 도,분,초로 나타낸 각도를 표현한다.
 * 위경도를 나타내기 위해 만들었다.
 * 북위 35도에서의 위경도 거리계산 메서드를 제공한다.
 */
@Embeddable
@NoArgsConstructor
@ToString
@Access(AccessType.FIELD)
public class DMS implements Comparable<DMS> {
    int degree, minute;
    double second;

    public int getDegree() {
        return degree;
    }
    public int getMinute() {
        return minute;
    }
    public double getSecond() {
        return second;
    }
    /**
     * 도,분,초로 나타낸 각도를 표현하는 DMS 객체를 생성한다.
     * @param degree 도(degree)
     * @param minute 분(minute)
     * @param second 초(second)
     */
    public DMS(int degree, int minute, double second) {
        this.degree = degree;
        this.minute = minute;
        this.second = second;
    }

    /**
     * 60진법 뺄셈으로 절댓값 차이를 구한다.
     * @param v 차이를 구할 DMS 객체
     * @return 두 DMS 객체의 차(항상 양수)
     */
    public DMS diffAbs(DMS v) {
        DMS u = this;
        if (u.compareTo(v) < 0) {
            u = v;
            v = this;
        } // u가 더 큰 것
        double d3 = u.second - v.second;
        int carry = 0;
        if (d3 < 0) {
            d3 += 60;
            carry = -1;
        }
        int d2 = u.minute - v.minute + carry;
        carry = 0;
        if (d2 < 0) {
            d2 += 60;
            carry = -1;
        }
        int d1 = u.degree - v.degree + carry; // d1은 반드시 0이상임
        return new DMS(d1, d2, d3);
    }

    /**
     * 두 DMS 객체를 비교하여 Ordering을 구한다.
     * @param v 비교 대상 DMS 객체
     * @return this가 크면 1, 같으면 0, v가 크면 -1
     */
    public int compareTo(DMS v) {
        if (degree > v.degree) return 1;
        if (degree < v.degree) return -1;
        if (minute > v.minute) return 1;
        if (minute < v.minute) return -1;
        return Double.compare(second, v.second);
    }

    /**
     * 이 DMS객체가 위도차를 나타낸 것으로 보고, 지표상에서의 길이로 변환한다
     * @return 미터로 변환된 길이
     */
    public double latitudeToMeterIn35Degree() {
        // 위도 35도 부근에서 위도 1도의 길이(meter)
        int latitudeMeter = 110941;
        return degree * latitudeMeter
                + minute * (latitudeMeter / 60.)
                + second * (latitudeMeter / 3600.);
    }

    /**
     * 이 DMS객체가 경도차를 나타낸 것으로 보고, 지표상에서의 길이로 변환한다
     * @return 미터로 변환된 길이
     */
    public double longitudeToMeterIn35Degree() {
        // 위도 35도에서 경도 1도의 길이(meter)
        int longitudeMeter = 91290;
        return degree * longitudeMeter
                + minute * (longitudeMeter / 60.)
                + second * (longitudeMeter / 3600.);
    }
}
