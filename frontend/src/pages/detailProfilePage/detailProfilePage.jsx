import { UserOutlined } from "@ant-design/icons";
import Avatar from "antd/lib/avatar/avatar";
import React, { useEffect, useRef, useState } from "react";
import { useCallback } from "react";
import { useHistory, useLocation } from "react-router-dom";
import { useRecoilValue } from "recoil";
import Header from "../../components/common/header/header";
import DetailProfileBottom from "../../components/detailProfilePage/detailProfileBottom/detailProfileBottom";
import DetailProfileCondition from "../../components/detailProfilePage/detailProfileCondition/detailProfileCondition";
import { userState } from "../../recoil/userState";
import styles from "./detailProfilePage.module.css";

const DetailProfilePage = (props) => {
  const userStates = useRecoilValue(userState);
  const history = useHistory();
  const location = useLocation();
  const selectorListRef = useRef();

  // useEffect lifecycle method를 사용하지 않은 이유 -> 마운트 되어 렌더링 그려지는 모습이 0.1초동안 보여지는 것을 방지
  const [selected, setSelected] = useState(location.state?.link || "내경로");

  const onHandleSelector = useCallback((e) => {
    Array.from(selectorListRef.current.children).map((item) => item.classList.remove(styles["selected__color"])); // 선택 색상 모두 삭제
    const target = e.target.closest("li");
    if (target) {
      const link = target.dataset.link;
      target.classList.add(styles["selected__color"]);
      setSelected(link);
    }
  }, []);

  if (!userStates.isLogin) {
    history.push("/");
  }

  useEffect(() => {
    const selectorList = Array.from(selectorListRef.current.children);
    const link = location.state?.link;
    if (link) {
      const selectedElement = selectorList.filter((el) => el.dataset.link === link).pop();
      selectedElement.classList.add(styles["selected__color"]); // 해당 영역에 색칠
    } else {
      selectorList[0].classList.add(styles["selected__color"]); // 기본 첫번째로 색칠
    }
    history.replace("/mypage", {}); // selected link state 초기화
  }, [history]);

  return (
    <div className={styles.DetailProfile}>
      <Header />
      <section className={styles.body}>
        <div className={styles.avatarBox}>
          <Avatar
            src={userStates.avatar}
            size={96}
            style={{
              border: "2px solid white",
            }}
            icon={<UserOutlined />}
          />
          <span className={styles.nickname}>{userStates.name}</span>
        </div>
        <ul ref={selectorListRef} className={styles.selectorBox} onClick={onHandleSelector}>
          <li className={styles.selector} data-link="내경로">
            <span className={styles.selector__title}>내 경로</span>
          </li>
          <li className={styles.selector} data-link="좋아요">
            <span className={styles.selector__title}>좋아요</span>
          </li>
          <li className={styles.selector} data-link="공유경로">
            <span className={styles.selector__title}>내 게시물</span>
          </li>
          <li className={styles.selector} data-link="리뷰">
            <span className={styles.selector__title}>리뷰</span>
          </li>
        </ul>
        <div className={styles.listView}>
          <DetailProfileCondition selected={selected} />
        </div>
      </section>
      <div className={styles.bottom}>
        <DetailProfileBottom />
      </div>
    </div>
  );
};

export default DetailProfilePage;
