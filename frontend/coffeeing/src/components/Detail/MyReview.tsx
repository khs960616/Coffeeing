import React from 'react';
import { ReviewProps } from './ReviewCard';
import editImg from 'assets/edit.svg';
import deleteImg from 'assets/delete.svg';
import { StarIcons } from 'components/StarIcons';

type MyReviewProps = {
  memberReview: ReviewProps;
  handleModal: () => void;
  beans?: string;
  handleDelete: () => void;
};

export const MyReview = (props: MyReviewProps) => {
  const { memberReview, handleModal, beans, handleDelete } = props;

  return (
    <div className="w-full h-200px bg-light rounded-lg p-6 flex flex-col">
      <div className="flex justify-between">
        <StarIcons score={memberReview.score} size="big" />
        <div>
          <button onClick={handleModal}>
            <img className="w-9 h-9" src={editImg} alt="수정" />
          </button>
          <button onClick={handleDelete}>
            <img className="w-9 h-9" src={deleteImg} alt="삭제" />
          </button>
        </div>
      </div>
      <p className="text-base h-10 w-full mt-3">{memberReview.content}</p>
    </div>
  );
};
