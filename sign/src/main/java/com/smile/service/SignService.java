package com.smile.service;

import com.smile.util.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.connection.BitFieldSubCommands;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import static com.smile.constant.Constant.NO_SIGN;

/**
 * @Description
 * @ClassName SignService
 * @Author smile
 * @date 2023.03.25 11:44
 */
@Service
public class SignService {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 签到
     *
     * @param uid
     * @param localDate
     * @return
     */
    public boolean sign(int uid, LocalDate localDate) {
        String signKey = getSignKey(uid, localDate);
        int offset = localDate.getDayOfMonth() - 1;
        return redisTemplate.opsForValue().setBit(signKey, offset, true);
    }

    /**
     * 是否签到
     *
     * @param uid
     * @param localDate
     * @return
     */
    public boolean isSign(int uid, LocalDate localDate) {
        String signKey = getSignKey(uid, localDate);
        int offset = localDate.getDayOfMonth() - 1;
        return redisTemplate.opsForValue().getBit(signKey, offset);
    }

    /**
     * 签到次数
     *
     * @param uid
     * @param date
     * @return
     */
    public long signCount(int uid, LocalDate date) {
        String signKey = getSignKey(uid, date);
        Long count = redisTemplate.execute((RedisCallback<Long>) conn -> conn.bitCount(signKey.getBytes()));
        return count == null ? 0L : count;
    }

    /**
     * 首次签到日期
     *
     * @param uid
     * @param localDate
     * @return
     */
    public String firstSignDay(int uid, LocalDate localDate) {
        String signKey = getSignKey(uid, localDate);
        Long offset = redisTemplate.execute((RedisCallback<Long>) conn -> conn.bitPos(signKey.getBytes(), true));
        if (offset >= 0) {
            LocalDate dayOfMonth = localDate.withDayOfMonth((int) (offset + 1));
            return DateUtil.format(dayOfMonth, "yyyy-MM-dd");
        }
        return NO_SIGN;
    }

    /**
     * 连续签到次数,从localDate往前日期判断
     * 31号偏移最大，对应的是低位值最小
     *
     * @param uid
     * @param localDate
     * @return
     */
    public long continuousSignCount(int uid, LocalDate localDate) {
        int count = 0;
        int dayOfMonth = localDate.getDayOfMonth();
        String signKey = getSignKey(uid, localDate);
        List<Long> list = redisTemplate.execute((RedisCallback<List<Long>>) conn ->
                conn.bitField(signKey.getBytes(),
                        BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(dayOfMonth)).valueAt(0)));
        if (!CollectionUtils.isEmpty(list)) {
            long v = list.get(0) == null ? 0 : list.get(0);
            for (int i = 0; i < dayOfMonth; i++) {
                /**
                 * 取低位连续不为0的个数即为连续签到次数，需考虑当天尚未签到的情况
                 */
                if (v >> 1 << 1 == v) {
                    if (i > 0) {
                        break;
                    }
                } else {
                    count += 1;
                }
                v >>= 1;
            }
        }
        return count;
    }

    /**
     * 月签到情况
     *
     * @param uid
     * @param localDate
     * @return
     */
    public TreeMap<String, Boolean> monthSign(int uid, LocalDate localDate) {
        int lengthOfMonth = localDate.lengthOfMonth();
        Map<String, Boolean> monthSignMap = new HashMap<>(lengthOfMonth);
        String signKey = getSignKey(uid, localDate);

        List<Long> list = redisTemplate.execute((RedisCallback<List<Long>>) conn ->
                conn.bitField(signKey.getBytes(),
                        BitFieldSubCommands.create().get(BitFieldSubCommands.BitFieldType.unsigned(lengthOfMonth)).valueAt(0)));
        if (!CollectionUtils.isEmpty(list)) {
            long v = list.get(0) == null ? 0L : list.get(0);
            for (int i = lengthOfMonth; i > 0; i--) {
                LocalDate date = localDate.withDayOfMonth(i);
                monthSignMap.put(DateUtil.format(date, "yyyy-MM-dd"), v >> 1 << 1 != v);
                v >>= 1;
            }
        }
        return new TreeMap<>(monthSignMap);
    }


    /**
     * 签到key
     *
     * @param uid
     * @param localDate
     * @return
     */
    private String getSignKey(int uid, LocalDate localDate) {
        String monthSuffix = DateUtil.format(localDate, "yyyyMM");
        return String.format("u:sign:%d:%s", uid, monthSuffix);
    }
}
