package com.github.aadt.kernel.event;

import com.github.aadt.kernel.actor.Actor;

public class Event {
        private String name;
        private Object data;
        private Actor receive_target;
        
        public Event(String event_name) {
                this.name = event_name;
        }
        
        public void set_data(Object data) {
                this.data = data;
        }
        
        public String get_name() {
                return name;
        }
        
        public Object get_data() {
                return data;
        }
        
        public void set_receive_target(Actor actor) {
                receive_target = actor;
        }
        
        public Actor get_receive_target() {
                return receive_target;
        }
}
