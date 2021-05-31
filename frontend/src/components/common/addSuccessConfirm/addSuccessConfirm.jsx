import React from "react";
import Portal from "../../portal/portal";
import styles from "./addSuccessConfirm.module.css";

const AddSuccessConfirm = ({ onClose, name }) => {
  return (
    <Portal>
      <div className={styles.AddSuccessConfirm}>
        <div>hi</div>
      </div>
    </Portal>
  );
};

export default AddSuccessConfirm;
