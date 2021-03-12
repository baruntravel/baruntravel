import Modal from "antd/lib/modal/Modal";
import React, { useCallback, useRef, useState } from "react";
import styles from "./portalCartBody.module.css";
import "antd/dist/antd.css";

import { useRecoilState } from "recoil";
import { myRouteCart } from "../../recoil/routeAtom";

const PortalCartBody = () => {
  const [modalVisible, setModalVisible] = useState(false);
  const [myRouteList, setMyRouteList] = useRecoilState(myRouteCart);
  const inputRef = useRef();

  const handleOpen = () => {
    setModalVisible(true);
  };
  const handleClose = () => {
    setModalVisible(false);
  };
  const handleOk = (event) => {
    event.preventDefault();
    setMyRouteList((prev) => {
      return [
        ...prev,
        {
          routeName: inputRef.current.value,
        },
      ];
    });
    inputRef.current.value = "";
    handleClose();
  };
  return (
    <div className={styles.PortalCartBody}>
      {myRouteList &&
        myRouteList.map((item) => (
          <div className={styles.title}>{item.routeName}</div>
        ))}
      <button className={styles.addBtn} onClick={handleOpen}>
        + 추가하기
      </button>
      <Modal
        title="새로운 여행 카트를 추가해요"
        style={{ marginTop: "10%" }}
        visible={modalVisible}
        onOk={handleOk}
        onCancel={handleClose}
        zIndex="1001"
      >
        <form className={styles.form} onSubmit={handleOk}>
          <input
            ref={inputRef}
            type="text"
            className={styles.input}
            placeholder="여행의 주제가 뭘까요?"
          />
        </form>
      </Modal>
    </div>
  );
};

export default PortalCartBody;
