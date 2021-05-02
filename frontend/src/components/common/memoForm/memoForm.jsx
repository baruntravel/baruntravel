import Form from "antd/lib/form/Form";
import TextArea from "antd/lib/input/TextArea";
import React, { useCallback, useRef } from "react";
import useInput from "../../../hooks/useInput";
import Portal from "../../portal/portal";
import styles from "./memoForm.module.css";
import { onWriteMemo } from "../../../api/cartAPI";

const MemoForm = ({ item, onClose, updateMemoShoppingItem }) => {
  const [textAreaContent, onChangeTextArea] = useInput("");

  const saveMemo = useCallback(async () => {
    const result = await onWriteMemo(item.id, textAreaContent);
    if (result) {
      updateMemoShoppingItem(item.id, textAreaContent);
    }
    onClose();
  }, [item.id, onClose, textAreaContent, updateMemoShoppingItem]);
  return (
    <Portal>
      <div className={styles.MemoForm}>
        <div className={styles.formBox}>
          <div className={styles.titleBox}>
            <span className={styles.title}>메모 작성</span>
          </div>
          <Form className={styles.form}>
            <TextArea
              className={styles.textArea}
              style={{ height: "100%" }}
              defaultValue={item.memo}
              value={textAreaContent}
              onChange={onChangeTextArea}
            />
          </Form>
          <div className={styles.confirmBox}>
            <div className={styles.close}>
              <span onClick={onClose}>취소</span>
            </div>
            <div className={styles.confirm}>
              <span onClick={saveMemo}>작성</span>
            </div>
          </div>
        </div>
      </div>
    </Portal>
  );
};

export default MemoForm;
