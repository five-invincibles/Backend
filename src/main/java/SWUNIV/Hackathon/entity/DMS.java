package SWUNIV.Hackathon.entity;

import javax.persistence.Embeddable;

/**
 * 도,분,초로 나타낸 각도를 표현한다.
 * 위경도를 나타내기 위해 만들었다.
 * 북위 35도에서의 위경도 거리계산 메서드를 제공한다.
 */
@Embeddable
public class DMS implements Comparable<DMS> {
    int _1, _2;
    double _3;

    public int getDegree() {
        return _1;
    }
    public int getMinute() {
        return _2;
    }
    /**
     * 도,분,초로 나타낸 각도를 표현하는 DMS 객체를 생성한다.
     * @param $1 도(degree)
     * @param $2 분(minute)
     * @param $3 초(second)
     */
    public DMS(int $1, int $2, double $3) {
        _1 = $1;
        _2 = $2;
        _3 = $3;
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
        double d3 = u._3 - v._3;
        int carry = 0;
        if (d3 < 0) {
            d3 += 60;
            carry = -1;
        }
        int d2 = u._2 - v._2 + carry;
        carry = 0;
        if (d2 < 0) {
            d2 += 60;
            carry = -1;
        }
        int d1 = u._1 - v._1 + carry; // d1은 반드시 0이상임
        return new DMS(d1, d2, d3);
    }

    /**
     * 두 DMS 객체를 비교하여 Ordering을 구한다.
     * @param v 비교 대상 DMS 객체
     * @return this가 크면 1, 같으면 0, v가 크면 -1
     */
    public int compareTo(DMS v) {
        if (_1 > v._1) return 1;
        if (_1 < v._1) return -1;
        if (_2 > v._2) return 1;
        if (_2 < v._2) return -1;
        return Double.compare(_3, v._3);
    }

    /**
     * 이 DMS객체가 위도차를 나타낸 것으로 보고, 지표상에서의 길이로 변환한다
     * @return 미터로 변환된 길이
     */
    public double latitudeToMeterIn35Degree() {
        // 위도 35도 부근에서 위도 1도의 길이(meter)
        int latitudeMeter = 110941;
        return _1 * latitudeMeter
                + _2 * (latitudeMeter / 60.)
                + _3 * (latitudeMeter / 3600.);
    }

    /**
     * 이 DMS객체가 경도차를 나타낸 것으로 보고, 지표상에서의 길이로 변환한다
     * @return 미터로 변환된 길이
     */
    public double longitudeToMeterIn35Degree() {
        // 위도 35도에서 경도 1도의 길이(meter)
        int longitudeMeter = 91290;
        return _1 * longitudeMeter
                + _2 * (longitudeMeter / 60.)
                + _3 * (longitudeMeter / 3600.);
    }
}
