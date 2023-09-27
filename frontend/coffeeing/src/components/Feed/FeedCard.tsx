import React, { useState } from "react";
import DefaultProfile from 'assets/feed/default-profile.svg'
import WriteIcon from 'assets/feed/write-icon.svg';
import DeleteIcon from 'assets/feed/delete-icon.svg';
import FeedUnlike from 'assets/feed/feed-unlike-icon.svg'
import Feedlike from 'assets/feed/feed-like-icon.svg'
import { FeedDetail } from "service/feed/types";
import { NavLink } from 'react-router-dom';
import { TagType } from "service/search/types";

interface FeedCardProps {
    feedDetail: FeedDetail,
    deleteEventHandler: (feedId: number)=>void,
    likeToggleEventHandler: (feedId: number)=>Promise<boolean|null>,
    editHandler: (feedDetail: FeedDetail)=>void
}

function FeedCard ({ feedDetail, deleteEventHandler, likeToggleEventHandler, editHandler }: FeedCardProps) {
  const [liked, setLiked] = useState<boolean>(feedDetail.isLike);
  
  const editEventHandler = () => {
    editHandler(feedDetail);
  }

  const toggleLike = async () => {
    const res = await likeToggleEventHandler(feedDetail.feedId);
    if(res!==null) {
        setLiked(res);
    }
  }

  return(
    <>
    <div className="feed-card flex flex-col w-full border-b-2 border-light-roasting">
        <div className="feed-header flex flew-row w-full px-22px py-3 justify-between">
            <div className="flex flex-row">
                <div className="feed-avater flex mr-10 justify-center items-center">
                    {
                        feedDetail.registerProfileImg ? 
                        <img src={feedDetail.registerProfileImg} className="w-12 h-12 rounded-full border-2"/> : 
                        <img src={DefaultProfile} />
                    }
                </div>
                <div className="feed-member-info flex flex-col">
                    <div className="font-bold text-base">
                        { feedDetail.registerName }
                    </div>
                    {
                        feedDetail.tag ? 
                        <div className="text-sm font-semibold text-white bg-light-roasting rounded px-3 py-1 scale-50 -translate-x-1/4"> 
                            <NavLink
                                to={`/detail/${feedDetail.tag.category === TagType.CAPSULE ? "capsule" : "coffee"}/${feedDetail.tag.tagId}`}>
                                {feedDetail.tag.name} 
                            </NavLink>
                        </div> : ""
                    }
                </div>
            </div>
            {
                feedDetail.isMine ?
                <div className="feed-control-button flex flex-row gap-4">
                    <div className="write-icon-wrapper cursor-pointer rounded-xl" onClick={editEventHandler}>
                        <img src = {WriteIcon} />
                    </div>
                    <div className="delete-icon-wrapper cursor-pointer rounded-xl" onClick={()=>{deleteEventHandler(feedDetail.feedId)}}>
                        <img src = {DeleteIcon} />
                    </div>
                </div>
                : ""
            }
        </div>

        <div className="feed-body flex flex-col w-full py-1">
            <div className="feed-image-wrapper flex w-full">
                <img src = {feedDetail.images[0].imageUrl} className="w-full"/>
            </div>

            <div className="feed-content-wrapper flex flex-col px-6">
                <div className="feed-like-wrapper w-full mx-1">
                    <div className="cursor-pointer w-fit rounded-xl"  onClick={toggleLike}>
                        { liked ? <img src = {Feedlike} /> : <img src = {FeedUnlike} /> }
                    </div>
                </div>

                <div className="feed-text-wrapper flex flex-row w-full min-h-max">
                    <p className="break-all"> 
                        { feedDetail.content }
                    </p>
                </div>
            </div>
        </div>
    </div>
    </>
  )
}

export default FeedCard;