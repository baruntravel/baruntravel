import Form from "antd/lib/form/Form";
import TextArea from "antd/lib/input/TextArea";
import React, { useCallback, useRef } from "react";
import useInput from "../../../hooks/useInput";
import Portal from "../../portal/portal";
import styles from "./memoForm.module.css";

const MemoForm = ({ item, onClose, updateShoppingCart }) => {
  const [textAreaContent, onChangeTextArea, setTextAreaContent] = useInput("");

  const saveMemo = useCallback(() => {
    console.log(textAreaContent);
  }, [textAreaContent]);
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
