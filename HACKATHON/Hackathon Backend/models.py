from sqlalchemy import Column, Integer, String, Boolean, DateTime, ForeignKey

from database import Base

class colors(Base):
    __tablename__ = "colors"
    id = Column(Integer, primary_key=True, index=True, autoincrement="auto")
    position = Column(Integer, unique=True,nullable=False)
    color = Column(String,default = "#FFFFFF",nullable=False)



