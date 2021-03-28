import React, { useState } from "react";
import styles from "./cartModal.module.css";
import "antd/dist/antd.css";
import { Modal, Button } from "antd";

import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faShoppingCart } from "@fortawesome/free-solid-svg-icons";

const CartModal = () => {
  const [isModalVisible, setIsModalVisible] = useState(false);

  const showModal = (e) => {
    setIsModalVisible(true);
    e.stopPropagation();
    console.log(e.target);
  };

  const handleOk = () => {
    setIsModalVisible(false);
  };

  const handleCancel = () => {
    setIsModalVisible(false);
  };

  return (
    <>
      <button className={styles.cartButton} onClick={showModal}>
        <FontAwesomeIcon icon={faShoppingCart} size={"sm"} />
      </button>
      <Modal
        title="Basic Modal"
        visible={isModalVisible}
        onOk={handleOk}
        onCancel={handleCancel}
      >
        <h1 className={styles.title}>나의 루트에 담기</h1>
        <li className={styles.routeName}></li>
      </Modal>
    </>
  );
};

export default CartModal;
